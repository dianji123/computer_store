package com.hzh.computer_store.service;


import com.hzh.computer_store.entity.District;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DistrictServiceTests {

    @Autowired
    private IDistrictService districtService;

    @Test
    public void findByParent() {
        //86代表中国,所有的省父代码号都是86
        List<District> districtList = districtService.getByParent("86");
        for (District district : districtList) {
            System.err.println(district);
        }
    }

    /**
     * 业务层只是调用持久层对应的方法然后返回,没有什么额外的实现,
     * 可以不用测试(一般超过8行的代码都要进行测试)
     */
//    @Test
//    public void getNameByCode() {
//        System.out.println(districtService.getNameByCode("610000"));
//    }


}
