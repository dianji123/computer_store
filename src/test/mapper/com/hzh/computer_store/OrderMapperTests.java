package com.hzh.computer_store;


import com.hzh.computer_store.entity.Order;
import com.hzh.computer_store.entity.OrderItem;
import com.hzh.computer_store.mapper.OrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMapperTests {

   @Autowired
   private OrderMapper orderMapper;

    @Test
    public void insertOrder() {
        Order order = new Order();
        order.setUid(31);
        order.setRecvName("dian");
        order.setRecvPhone("133333");
        orderMapper.insertOrder(order);
    }

    @Test
    public void insertOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setOid(1);
        orderItem.setPid(10000001);
        orderItem.setTitle("高档铅笔");
        orderMapper.insertOrderItem(orderItem);
    }

}
