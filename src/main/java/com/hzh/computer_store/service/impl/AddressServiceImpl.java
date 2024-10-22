package com.hzh.computer_store.service.impl;

import com.hzh.computer_store.entity.Address;
import com.hzh.computer_store.entity.User;
import com.hzh.computer_store.mapper.AddressMapper;
import com.hzh.computer_store.mapper.UserMapper;
import com.hzh.computer_store.service.IAddressService;
import com.hzh.computer_store.service.IDistrictService;
import com.hzh.computer_store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**新增收货地址实现类*/
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IDistrictService districtService;

    /**
     * 为了方便日后修改最大收货地址数量,可以在配置文件
     * application.properties中定义user.address.max-count=20
     */
    //spring读取配置文件中数据:@Value("${user.address.max-count}")
    @Value("${user.address.max-count}")
    private Integer maxCount;

    //新增收货地址
    @Override
    public void addNewAddress(Integer uid, String username, Address address) {

        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UsernameNotFoundException("用户数据不存在");
        }

        //统计收货地址数量
        Integer count = addressMapper.countByUid(uid);
        if (count >= maxCount) {
            throw new AddressCountLimitException("用户收货地址超出数量上限");
        }

        //如果是第一条收获地址,设置为默认地址
        address.setUid(uid);
        Integer isDefault = count == 0 ? 1 : 0; //1代表默认收货地址
        address.setIsDefault(isDefault);

        //添加修改日志
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());

        //补全address对象中的数据
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        //插入收货地址
        Integer rows = addressMapper.insert(address);
        if (rows != 1) {
            throw new InsertException("插入用户的收货地址时产生未知异常");
        }
    }

    //获取用户的所有地址
    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> addressList = addressMapper.findByUid(uid);
        /**
         * 收货地址列表在前端只展示了四个数据,这里让其他值为空降低数据量
         * ProvinceName,CityName,AreaName,aid,tel(确认订单页展示展示用户地
         * 址时用到tel)后面提交订单时的地址会用到tel,所以这里不设为null
         * */
        for (Address address: addressList) {
            //address.setAid(null);
            address.setUid(null);
            //address.setProvinceName(null);
            address.setProvinceCode(null);
            //address.setCityName(null);
            address.setCityCode(null);
            //address.setAreaName(null);
            address.setAreaCode(null);
            address.setZip(null);
            //address.setTel(null);
            address.setIsDefault(null);
            address.setCreatedTime(null);
            address.setCreatedUser(null);
            address.setModifiedTime(null);
            address.setModifiedUser(null);
        }
        return addressList;
    }

    //修改某个用户的某条收货地址数据为默认收货地址
    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        //检测数据是否存在
        Address result = addressMapper.findByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("收货地址不存在");
        }

        //检测当前获取到的收货地址数据的归属
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }

        //将所有地址设为非默认
        Integer rows = addressMapper.updateNonDefault(uid);
        if (rows < 1) {
            throw new UpdateException("更新数据时产生未知的异常");
        }

        //将用户选中的地址设为默认
        rows = addressMapper.updateDefaultByAid(aid, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知的异常");
        }

    }

    //删除用户选中的收货地址
    @Override
    public void delete(Integer aid, Integer uid, String username) {
        //检测数据是否存在
        Address result = addressMapper.findByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("收货地址数据不存在");
        }

        //检测数据归属
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }

        //执行删除
        Integer rows = addressMapper.deleteByAid(aid);
        if (rows != 1) {
            throw new DeleteException("删除数据时产生未知的异常");
        }

        //如果删除的是非默认地址,终止程序
        if (result.getIsDefault() == 0) {
            return;
        }

        //如果没有剩余的地址,终止程序
        Integer count = addressMapper.countByUid(uid);
        if (count == 0) {
            return;
        }

        //查询用户最新修改的一条数据
        Address address = addressMapper.findLastModified(uid);
        //把最新的一条数据设置为默认收货地址
        rows = addressMapper.updateDefaultByAid(address.getAid(), username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知的异常");
        }

    }

    //获取选中的收货地址的详细数据
    @Override
    public Address getByAid(Integer aid, Integer uid) {
        //检验数据是否存在和数据归属
        Address address = addressMapper.findByAid(aid);
        if (address == null) {
            throw new AddressNotFoundException("收货地址数据不存在的异常");
        }
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }

        //减少数据体量
        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);

        return address;
    }
}
