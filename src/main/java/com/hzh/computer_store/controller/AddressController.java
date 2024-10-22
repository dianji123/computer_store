package com.hzh.computer_store.controller;

import com.hzh.computer_store.entity.Address;
import com.hzh.computer_store.service.IAddressService;
import com.hzh.computer_store.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("addresses")
public class AddressController extends BaseController{

    @Autowired
    private IAddressService addressService;

    //新增地址
    @RequestMapping("add_new_address")
    public JsonResult<Void> addNewAddress(Address address, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.addNewAddress(uid, username, address);
        return new JsonResult<>(OK, "新增收货地址成功");
    }

    //展示用户的地址列表
    @RequestMapping({"/", ""})
    public JsonResult<List<Address>> getByUid(HttpSession session) {
        Integer uid = getUidFromSession(session);
        List<Address> data = addressService.getByUid(uid);
        return new JsonResult<>(OK, data);
    }

    //设置默认地址
    //ResetFul风格的请求编写
    //RestFul编写时不管参数名和占位符是否一致都必须加@PathVariable("aid")
    @RequestMapping("{aid}/set_default")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid, HttpSession session) {
        addressService.setDefault(aid, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult<>(OK, "默认地址设置成功");
    }

    //删除地址
    //ResetFul风格的请求编写
    //RestFul编写时不管参数名和占位符是否一致都必须加@PathVariable("aid")
    @RequestMapping("{aid}/delete")
    public JsonResult<Void> delete(@PathVariable("aid") Integer aid, HttpSession session) {
        addressService.delete(aid, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult<>(OK, "地址删除成功");
    }

}
