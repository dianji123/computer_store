package com.hzh.computer_store.controller;

import com.hzh.computer_store.service.ICartService;
import com.hzh.computer_store.utils.JsonResult;
import com.hzh.computer_store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("carts")
public class CartController extends BaseController{

    @Autowired
    private ICartService cartService;

    //商品加入购物车
    @RequestMapping("add_to_cart")
    public JsonResult<Void> addToCart(Integer pid, Integer amount, HttpSession session) {
        cartService.addToCart(getUidFromSession(session), pid, amount, getUsernameFromSession(session));
        return new JsonResult<Void>(OK, "添加成功");
    }

    //查询购物车里的商品
    @RequestMapping({"", "/"})
    public JsonResult<List<CartVO>> getVOByUid(HttpSession session) {
        List<CartVO> data = cartService.getVOByUid(getUidFromSession(session));
        return new JsonResult<List<CartVO>>(OK, "查询成功", data);
    }

    //增加购物车里某个商品的数量 一次请求增加一个
    @RequestMapping("{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer data = cartService.addNum(cid, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult<Integer>(OK, "增加成功", data);
    }

    //减少购物车里某个商品的数量 一次请求减少一个
    @RequestMapping("{cid}/num/sub")
    public JsonResult<Integer> subNum(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer data = cartService.subNum(cid, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult<Integer>(OK, "减少成功", data);
    }

    //删除购物车里的某个商品
    @RequestMapping("{cid}/num/delete")
    public JsonResult<Void> deleteByCid(@PathVariable("cid") Integer cid, HttpSession session) {
        cartService.deleteByCid(cid, getUidFromSession(session));
        return new JsonResult<Void>(OK, "删除成功");
    }

    //提交多个商品
    @RequestMapping("list")
    public JsonResult<List<CartVO>> findVOByCids(Integer[] cids, HttpSession session) {
        List<CartVO> data = cartService.getVOByCids(getUidFromSession(session), cids);
        return new JsonResult<>(OK, data);
    }

}
