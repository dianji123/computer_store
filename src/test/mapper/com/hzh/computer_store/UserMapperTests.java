package com.hzh.computer_store;

import com.hzh.computer_store.entity.User;
import com.hzh.computer_store.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

//@SpringBootTest表示当前的类是一个测试类,不会随同项目一块打包
@SpringBootTest

@RunWith(SpringRunner.class)
public class UserMapperTests {

/**
 * 1.@RunWith表示启动这个单元测试类,需要传递一个参数,该参数必须是SpringRunner.class即SpringRunner的实例类型
 * 单元测试方法:被@Test注解修饰;返回值类型void;没有参数;方法的访问修饰符必须是public
 */
    @Autowired //注入依赖 值的初始化
    private UserMapper userMapper;

    @Test
    public void insert() {
        User user = new User();
        user.setUsername("李四");
        user.setPassword("123456");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void findByUsername() {
        User user = userMapper.findByUsername("kk");
        System.out.println(user);
    }

    @Test
    public void findByUid() {
        User user = userMapper.findByUid(10);
        System.out.println(user);
    }

    @Test
    public void updatePasswordByUid() {
        userMapper.updatePasswordByUid(10, "000000", "admin", new Date());
    }

    @Test
    public void updateInfoByUid() {
        User user = new User();
        user.setUid(11);
        user.setPhone("15512345678");
        user.setEmail("1737@qq.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }

    @Test
    public void updateAvatarByUid() {
        userMapper.updateAvatarByUid(12, "abc1", "admin", new Date());
    }

}

