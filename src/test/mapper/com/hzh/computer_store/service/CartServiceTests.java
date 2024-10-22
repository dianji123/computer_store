package com.hzh.computer_store.service;

import com.hzh.computer_store.entity.Cart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartServiceTests {

    @Autowired
    private ICartService cartService;

    @Test
    public void addToCart() {
        cartService.addToCart(11, 10000002, 10, "Tom");
    }

    @Test
    public void getVOByUid() {
        System.out.println(cartService.getVOByUid(11));
    }

    @Test
    public void addNum() {
        System.out.println(cartService.addNum(2, 11, "kk"));
    }

    @Test
    public void subNum() {
        System.out.println(cartService.subNum(2, 11, "kk"));
    }

    @Test
    public void deleteByCid() {
        cartService.deleteByCid(9, 3);
    }

    @Test
    public void getVOByCids() {
        Integer[] cids = {10,13};
        System.out.println(cartService.getVOByCids(3, cids));
    }

}
