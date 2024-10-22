package com.hzh.computer_store.service;

import com.hzh.computer_store.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTests {

    @Autowired
    private IProductService productService;

    @Test
    public void findHotList() {
        List<Product> productList = productService.findHotList();
        System.out.println(productList);
    }

    @Test
    public void findById() {
        Product product = productService.findById(10000001);
        System.out.println(product);
    }

}
