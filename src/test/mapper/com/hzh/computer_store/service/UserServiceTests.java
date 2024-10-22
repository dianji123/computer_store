package com.hzh.computer_store.service;

import com.hzh.computer_store.entity.User;
import com.hzh.computer_store.mapper.UserMapper;
import com.hzh.computer_store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest

@RunWith(SpringRunner.class)
public class UserServiceTests {

    @Autowired //值的初始化
    private IUserService userService;

    @Test
    public void reg() {
        try {
            User user = new User();
            user.setUsername("oo");
            user.setPassword("123456");
            userService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            //获取异常对象再获取类的名称然后输出
            System.out.println(e.getClass().getSimpleName());
            //输出异常信息(是自己在ServiceException的子类具体设置的信息
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login() {
        User user = userService.login("diyou", "123456");
        System.out.println(user);
    }

    @Test
    public void changePassword() {
        userService.changePassword(12, "admin", "000000", "123456");
    }

    @Test
    public void getByUid() {
        //err 输出红色信息
        System.err.println(userService.getByUid(12).getUsername());
    }

    @Test
    public void changeInfo() {
        User user = new User();
        user.setPhone("15512345678");
        user.setEmail("1234@qq.com");
        //根据要求，不能修改用户名
        user.setUsername("qwe");
        user.setGender(0);
        userService.changeInfo(12, user);
    }

    @Test
    public void changeAvatar() {
        userService.changeAvatar(12, "serviceTest", "admin");
    }

}

