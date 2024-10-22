package com.hzh.computer_store.service;

import com.hzh.computer_store.entity.User;

/**用户模块业务层接口*/
public interface IUserService {

    /**
     * 用户注册方法
     * @param user 用户的数据对象
     */
    void reg(User user);

    /**
     * 用户登录功能
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 当前匹配的用户数据, 如果没有则返回null
     */
    User login(String username, String password);

    /**
     * 用户修改密码
     * param:uid,password,modifiedUser,modifiedTime
     * modifiedUser就是username,已经保存在session中
     * 更新数据之前需要先根据uid查这个数据存不存在,已经保存在session中
     * newpassword和oldpassword
     * 修改时间不需要在参数列表,直接在方法内部new Date()就可以了
     */
    void changePassword(Integer uid, String username, String oldPassword, String newPassword);

    /**
     * 根据uid查询用户数据
     * @param uid
     * @return 用户数据
     */
    User getByUid(Integer uid);

    /**
     * 用户修改个人信息
     * @param uid 通过控制层在session中获取然后传递给业务层,并在业务层封装到User对象中
     */
    void changeInfo(Integer uid, User user);

    /**
     * 用户上传头像
     * @param uid
     * @param avatar
     * @param username
     */
    void changeAvatar(Integer uid, String avatar, String username);
}
