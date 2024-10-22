package com.hzh.computer_store.controller;

import com.hzh.computer_store.entity.Product;
import com.hzh.computer_store.service.IProductService;
import com.hzh.computer_store.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("products")
public class ProductController extends BaseController{

    @Autowired
    private IProductService productService;

    //查询热销商品的前四名
    @RequestMapping("hot_list")
    public JsonResult<List<Product>> getHotList() {
        List<Product> data = productService.findHotList();
        return new JsonResult<List<Product>>(OK, "查询成功", data);
    }


    //查询商品详情 目前只能查看热销商品
    //RestFul
    //@GetMapping 把HTTP get请求映射到特定处理程序的方法注解
    @GetMapping("{id}/details")
    public JsonResult<Product> getById(@PathVariable("id") Integer id) {
        Product data = productService.findById(id);
        return new JsonResult<>(OK, "查询成功", data);
    }

}
