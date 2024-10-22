package com.hzh.computer_store.mapper;

import com.hzh.computer_store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface UserMapper {

    /**
     * 注册用户的数据
     * @return 受影响的行数
     * */
    Integer insert(User user);

    /**
     * 根据用户名查询用户的数据
     * @return 如果找到对应的用户则返回这个用户的数据,如果没有找到则返回null
     * */
    User findByUsername(String username);

    /**
     * 根据用户的id查询用户的数据
     * @param uid 用户的id
     * @return 如果找到则返回User对象,反之返回null值
     */
    User findByUid(Integer uid);

    /**
     * 根据用户的uid来修改用户密码
     * @param uid 用户的id
     * @param password 用户输入的新密码
     * @param modifiedUser 表示修改的执行者
     * @param modifiedTime 表示修改数据的时间
     * @return 返回值为受影响的行数
     */
    //对于mapper接口中，传入的参数有多个时必须使用@Param进行标识
    //@Param("参数名") 参数名与映射文件中sql语句的#{变量名}一致,方法的参数名可以与@Param("参数名")不同
    //@Param("参数名") 把传入的参数注入到该方法的参数中
    //org.apache.ibatis.annotations.Param;
    Integer updatePasswordByUid(@Param("uid") Integer uid,@Param("password") String password,
                                @Param("modifiedUser") String modifiedUser,@Param("modifiedTime") Date modifiedTime);

    /**
     * 根据uid修改个人信息 phone email gender
     * @param user
     * @return 返回值为受影响的行数
     */
    Integer updateInfoByUid(User user);

    /**
     * 根据uid修改用户头像
     * @param uid 用户id
     * @param avatar 用户头像路径
     * @param modifiedUser 修改人
     * @param modifiedTime 修改时间
     */
    //对于mapper接口中，传入的参数有多个时必须使用@Param进行标识
    //@Param("参数名") 和方法的参数名可以不同 @Param("uid") Integer id
    Integer updateAvatarByUid(@Param("uid") Integer uid,@Param("avatar") String avatar,
                              @Param("modifiedUser") String modifiedUser,@Param("modifiedTime") Date modifiedTime);

}
