package com.hzh.computer_store.service;

import com.hzh.computer_store.entity.District;

import java.util.List;

public interface IDistrictService {

    /**
     * 根据父代码号查询区域信息 省/市/区
     * @param parent 父代码号
     * @return 多个区域的信息
     */
    List<District> getByParent(String parent);

    /**
     * 通过子代码号查询省市区名称
     * @param code 子代码号
     * @return 对应省市区名称
     */
    String getNameByCode(String code);

}
