package com.hzh.computer_store.controller;

import com.hzh.computer_store.entity.Order;
import com.hzh.computer_store.service.IOrderService;
import com.hzh.computer_store.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("orders")
public class OrderController extends BaseController{

    @Autowired
    private IOrderService orderService;

    //创建订单
    @RequestMapping("create")
    public JsonResult<Order> create(Integer aid, Integer[] cids, HttpSession session) {
        Order data = orderService.create(aid, cids, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult<>(OK, "订单创建成功", data);
    }


}
