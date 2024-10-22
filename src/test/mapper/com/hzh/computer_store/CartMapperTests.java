package com.hzh.computer_store;

import com.hzh.computer_store.entity.Cart;
import com.hzh.computer_store.mapper.CartMapper;
import com.hzh.computer_store.vo.CartVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartMapperTests {

   @Autowired
    private CartMapper cartMapper;

   @Test
    public void insert() {
       Cart cart = new Cart();
       cart.setUid(11);
       cart.setPid(1000001);
       cart.setNum(3);
       cart.setPrice(4L);
       System.out.println(cartMapper.insert(cart));
   }

   @Test
    public void updateNumByCid() {
       cartMapper.updateNumByCid(1, 4, "admin", new Date());
   }

    @Test
    public void findByUidAndPid() {
        Cart cart = cartMapper.findByUidAndPid(11, 1000001);
        System.out.println(cart);
    }

    @Test
    public void getVOByUid() {
        List<CartVO> cartVOList = cartMapper.findVOByUid(11);
        System.out.println(cartVOList);
    }

    @Test
    public void findByCid() {
        System.out.println(cartMapper.findByCid(1));
    }

    @Test
    public void deleteByCid() {
         System.out.println(cartMapper.deleteByCid(8, 3));
    }

    @Test
    public void findVOByCids() {
       Integer[] cids = {1, 2, 3, 8, 200};
        List<CartVO> cartVOList = cartMapper.findVOByCids(cids);
        for (CartVO item : cartVOList) {
            System.out.println(item);
        }
    }

}
