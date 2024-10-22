package com.hzh.computer_store.service.impl;


import com.hzh.computer_store.entity.Product;
import com.hzh.computer_store.mapper.ProductMapper;
import com.hzh.computer_store.service.IProductService;
import com.hzh.computer_store.service.ex.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/** 处理商品数据的业务层实现类 */
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    //查询热销排行榜前四
    @Override
    public List<Product> findHotList() {
        List<Product> productList = productMapper.findHotList();
        for (Product product : productList) {
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return productList;
    }

    //查询商品详情
    @Override
    public Product findById(Integer id) {
        Product product = productMapper.findById(id);
        if (product == null) {
            throw new ProductNotFoundException("尝试访问的商品数据不存在");
        }

        //减少数据体量
        product.setPriority(null);
        product.setCreatedUser(null);
        product.setCreatedTime(null);
        product.setModifiedUser(null);
        product.setModifiedTime(null);

        return product;
    }
}
