package com.hzh.computer_store.service;

import com.hzh.computer_store.entity.Address;
import java.util.List;

/**收货地址业务层接口*/
public interface IAddressService {

    /**
     * 用户新增收获地址
     * @param uid 查询用户地址总数及新建地址
     * @param username 用来修改数据库创建人/修改人字段
     * @param address 新增的地址
     */
    void addNewAddress(Integer uid, String username, Address address);

    /**
     * 获取用户的所有地址
     * @param uid 用户uid
     */
    List<Address> getByUid(Integer uid);

    /**
     * 修改某个用户的某条收货地址数据为默认收货地址
     * @param aid 收货地址的id
     * @param uid 用户id
     * @param username 修改人
     */
    void setDefault(Integer aid, Integer uid, String username);

    /**
     * 删除用户选中的收货地址数据
     * @param aid 收货地址id
     * @param uid 用户id
     * @param username 操作人
     */
    void delete(Integer aid, Integer uid, String username);

    /**
     * 获取选中的收货地址的详细数据
     * @param aid 收货地址id
     * @param uid 用户id
     * @return 对应的地址数据
     */
    Address getByAid(Integer aid, Integer uid);
}
