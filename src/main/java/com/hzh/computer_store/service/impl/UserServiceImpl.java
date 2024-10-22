package com.hzh.computer_store.service.impl;

import com.hzh.computer_store.entity.User;
import com.hzh.computer_store.mapper.UserMapper;
import com.hzh.computer_store.service.IUserService;
import com.hzh.computer_store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/**因为要将这个实现类交给spring管理,所以需要在类上加 @Service*/
@Service
public class UserServiceImpl implements IUserService {

    //reg方法核心就是调用mapper层的方法,所以要声明UserMapper对象并加@Autowired注解
    @Autowired
    private UserMapper userMapper;

    //注册用户
    @Override
    public void reg(User user) {
        String username = user.getUsername();
        //调用mapper的findByUsername(username)判断用户是否被注册过了
        User result = userMapper.findByUsername(username);
        if (result != null) {
            throw new UsernameDuplicatedException("用户名被占用");
        }

        user.setUsername(username);

        /**
         * 密码加密处理作用:
         * 1.后端不再能直接看到用户的密码
         * 2.忽略了密码原来的强度,提升了数据安全性
         * 密码加密处理的实现:
         * 串+password+串->交给md5算法连续加密三次
         * 串就是数据库字段中的盐值,是一个随机字符串
         */

        String oldPassword = user.getPassword();
        //1.随机生成一个盐值(大写的随机字符串)
        String salt = UUID.randomUUID().toString().toUpperCase();
        //2.将密码和盐值作为一个整体进行加密处理
        String md5Password = getMD5Password(oldPassword, salt);
        //3.将盐值保存到数据库
        user.setSalt(salt);
        //4.将加密之后的密码重新补全设置到user对象当中
        user.setPassword(md5Password);

        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        //执行注册业务功能的实现
        Integer rows = userMapper.insert(user);
        if (rows != 1) {
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }

    }

    @Override
    public User login(String username, String password) {
        User result = userMapper.findByUsername(username);
        if (result == null) {
            throw new UsernameNotFoundException("用户数据不存在");
        }

        /**
         * 检测用户的密码是否匹配:
         * 1.先获取数据库中加密之后的密码
         * 2.和用户传递过来的密码进行比较
         *  2.1先获取盐值
         *  2.2将获取的用户密码按照相同的md5算法加密
         */
        String oldPassword = result.getPassword();
        String salt = result.getSalt();
        String newMDdPassword = getMD5Password(password, salt);
        if (!newMDdPassword.equals(oldPassword)) {
            throw new PasswordNotMatchException("用户密码错误");
        }

        //判断is_delete字段的值是否为1,为1表示被标记为删除
        if (result.getIsDelete() == 1) {
            throw new UsernameNotFoundException("用户数据不存在");
        }

        //方法login返回的用户数据是为了辅助其他页面做数据展示使用(只会用到uid,username,avatar)
        //所以可以new一个新的user只赋这三个变量的值,这样使层与层之间传输时数据体量变小,后台层与
        // 层之间传输时数据量越小性能越高,前端也是的,数据量小了前端响应速度就变快了
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        user.setCreatedUser(result.getCreatedUser());
        user.setCreatedTime(result.getCreatedTime());
        user.setModifiedUser(result.getModifiedUser());
        user.setModifiedTime(result.getModifiedTime());

        return user;
    }

    //修改密码
    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        //用户不存在或用户已注销
        if (result == null || result.getIsDelete() == 1) {
            throw new UsernameNotFoundException("用户数据不存在");
        }
        //原密码与数据库中密码比较
        String oldMd5Password = getMD5Password(oldPassword, result.getSalt());
        if (!result.getPassword().equals(oldMd5Password)) {
            throw new PasswordNotMatchException("密码错误");
        }
        //加密新的密码到数据库，使用以前的盐值
        String newMd5Password = getMD5Password(newPassword, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据产生未知的异常");
        }
    }

    //uid查找用户数据
    @Override
    public User getByUid(Integer uid) {
        //查询用户是否存在
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UsernameNotFoundException("用户数据不存在");
        }

        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());

        return user;
    }

    //修改个人信息
    /**
     *页面上展示的User对象中的数据只有phone,email,gender,username,因为springboot进行依赖
     * 注入的时候只注入表单中数据的值,所以需要手动将uid封装到user中
     */
    @Override
    public void changeInfo(Integer uid, User user) {
        //查询用户是否存在
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UsernameNotFoundException("用户数据不存在");
        }
        //根据要求，不能修改用户名，这里不做实现
        user.setUid(uid);
        user.setModifiedUser(user.getUsername());
        user.setModifiedTime(new Date());

        Integer rows = userMapper.updateInfoByUid(user);
        if (rows != 1) {
            throw new UpdateException("更新数据时产生异常");
        }
    }

    //用户修改头像
    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        //查询用户是否存在
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UsernameNotFoundException("用户数据不存在");
        }
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新用户头像时产生未知异常");
        }
    }




    //md5加密算法
    private String getMD5Password(String password,String salt) {
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }


}
