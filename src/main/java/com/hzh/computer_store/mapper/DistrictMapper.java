package com.hzh.computer_store.mapper;

import com.hzh.computer_store.entity.District;

import java.util.List;

public interface DistrictMapper {

    /**
     * 根据父代码号查询区域信息
     * @param parent 父代码号
     * @return 某个父区域下所有的区域列表 可能有多个结果,使用List
     */
    List<District> findByParent(String parent);

    /**
     * 通过子代码号查询省市区名称
     * @param code 子代码号
     * @return 对应省市区名称
     */
    String findNameByCode(String code);

}
