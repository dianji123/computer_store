package com.hzh.computer_store;

import com.hzh.computer_store.entity.Product;
import com.hzh.computer_store.mapper.ProductMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductMapperTests {

   @Autowired
    private ProductMapper productMapper;

   @Test
    public void findHotList() {
       List<Product> productList = productMapper.findHotList();
       System.out.println(productList);
   }

   @Test
    public void findById() {
       Product product = productMapper.findById(10000001);
       System.out.println(product);
   }

}
