package com.hzh.computer_store.mapper;

import com.hzh.computer_store.entity.Address;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface AddressMapper {

    /**
     * 插入用户的收货地址数据
     * @param address
     * @return 受影响的行数
     */
    Integer insert(Address address);

    /**
     * 根据uid统计用户的收获地址数量
     * @param uid
     * @return 当前用户的收货地址总数
     */
    Integer countByUid(Integer uid);

    /**
     * 收货地址列表展示
     * @param uid 用户uid
     * @return 收货地址数据
     */
    List<Address> findByUid(Integer uid);

    /**
     * 根据aid查询收货地址数据
     * @param aid 收货地址aid
     * @return 收货地址数据,没有返回null
     */
    Address findByAid(Integer aid);

    /**
     * 根据用户uid修改用户的收货地址统一设置为非默认
     * @param uid 用户uid
     * @return 受影响的行数
     */
    Integer updateNonDefault(Integer uid);

    /**
     * 将用户选中的这条记录设置为默认收货地址
     * @param aid 收货地址aid
     * @param modifiedUser 修改人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateDefaultByAid(@Param("aid") Integer aid,
                               @Param("modifiedUser") String modifiedUser,
                               @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据收货地址aid删除收货地址数据
     * @param aid 收货地址的id
     * @return 受影响的行数
     */
    Integer deleteByAid(Integer aid);

    /**
     * 根据用户uid查询用户最后一次被修改的收货地址数据
     * @param uid 用户id
     * @return 收货地址数据
     */
    Address findLastModified(Integer uid);


}
