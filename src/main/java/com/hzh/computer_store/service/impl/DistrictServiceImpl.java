package com.hzh.computer_store.service.impl;

import com.hzh.computer_store.entity.District;
import com.hzh.computer_store.mapper.DistrictMapper;
import com.hzh.computer_store.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**查询省市区信息实现类*/
@Service
public class DistrictServiceImpl implements IDistrictService {

    @Autowired
    private DistrictMapper districtMapper;

    //获取省市区列表
    @Override
    public List<District> getByParent(String parent) {
        List<District> districtList = districtMapper.findByParent(parent);
        /**
         * 在进行网络数据传输时,为了尽量避免无效数据的传递,可以将无效数据
         * 设置为null,这样既节省流量,又提升了效率
         */
        for (District district : districtList) {
            district.setId(null);
            district.setParent(null);
        }
        return districtList;
    }

    //获取准确的省市区名称
    @Override
    public String getNameByCode(String code) {
        return districtMapper.findNameByCode(code);
    }
}
