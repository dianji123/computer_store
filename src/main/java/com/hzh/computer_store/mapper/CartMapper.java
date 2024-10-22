package com.hzh.computer_store.mapper;

import com.hzh.computer_store.entity.Cart;
import com.hzh.computer_store.vo.CartVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CartMapper {

    /**
     * 插入购物车数据
     * @param cart 购物车数据
     * @return 受影响的行数
     */
    Integer insert(Cart cart);

    /**
     * 修改购物车数据中商品的数量
     * @param cid 购物车数据id
     * @param num 数量
     * @param modifiedUser 执行人
     * @param modifiedTime 执行时间
     * @return 受影响的行数
     */
    //mapper接口,有多个参数时,使用@Param
    Integer updateNumByCid(@Param("cid") Integer cid, @Param("num") Integer num,
                           @Param("modifiedUser") String modifiedUser,
                           @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据用户id和商品id查询购物车中的数据
     * @param uid 用户id
     * @param pid 商品id
     * @return 匹配的购物车数据,如果该用户的购物车中没有该商品,返回null
     */
    Cart findByUidAndPid(@Param("uid") Integer uid, @Param("pid") Integer pid);

    /**
     * 查询某用户的购物车数据
     * @param uid 用户id
     * @return 该用户的购物车数据的列表
     */
    List<CartVO> findVOByUid(Integer uid);

    /**
     * 查询购物车数据
     * @param cid 购物车id
     * @return 购物车数据 | null
     */
    Cart findByCid(Integer cid);

    /**
     * 删除购物车里的商品
     * @param cid 购物车id
     * @param uid 用户id
     * @return 受影响的行数
     */
    Integer deleteByCid(@Param("cid") Integer cid, @Param("uid") Integer uid);

    /**
     * 根据若干个不确定的id值,查询购物车数据
     * @param cids 购物车ids
     * @return 购物车数据集合
     */
    List<CartVO> findVOByCids(Integer[] cids);

}
