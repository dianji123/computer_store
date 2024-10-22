package com.hzh.computer_store.service;

import com.hzh.computer_store.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceTests {

    @Autowired
    private IOrderService orderService;

    @Autowired
    IUserService userService;

    @Test
    public void create() {
        Integer[] cids = {3,10,13};
        Order order = orderService.create(8, cids, 3, "admin");
        System.out.println(order);
    }

}
