package com.hzh.computer_store.controller;


import com.hzh.computer_store.entity.District;
import com.hzh.computer_store.service.IDistrictService;
import com.hzh.computer_store.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("districts")
public class DistrictController extends BaseController{

    @Autowired
    private IDistrictService districtService;

    /**
     * 请求路径(http://yourdomain.com/districts/)和父路径(/districts)相同时用@RequestMapping({"/",""}),表
     * 示districts后面跟/或者什么也不跟都会进入这个方法
     * 多个路径要手动添加{}
     */
    @RequestMapping({"/", ""})
    public JsonResult<List<District>> getByParent(String parent) {
        List<District> data = districtService.getByParent(parent);
        return new JsonResult<>(OK, "区域信息列表获取成功", data);
    }


}
