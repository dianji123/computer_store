package com.hzh.computer_store.service;

import com.hzh.computer_store.entity.Cart;
import com.hzh.computer_store.vo.CartVO;

import java.util.List;

/**加入购物车业务层接口*/
public interface ICartService {

    /**
     * 将商品添加到购物车
     * @param uid 当前登录用户id
     * @param pid 商品id
     * @param amount 增加的数量
     * @param username 当前登录的用户名
     */
    void addToCart(Integer uid, Integer pid, Integer amount, String username);

    /**
     * 查询某用户的购物车数据
     * @param uid 当前登录用户id
     * @return 该用户的购物车数据的列表 |null
     */
    List<CartVO> getVOByUid(Integer uid);

    /**
     * 增加用户的购物车中某商品的数量
     * @param cid 购物车id
     * @param uid 当前登录用户id
     * @param username 当前登录用户
     * @return 更新后的商品数量
     */
    Integer addNum(Integer cid, Integer uid, String username);

    /**
     * 减少用户的购物车中某商品的数量
     * @param cid 购物车id
     * @param uid 当前登录用户id
     * @param username 当前登录用户
     * @return 更新后的商品数量
     */
    Integer subNum(Integer cid, Integer uid, String username);

    /**
     * 删除购物车里的商品
     * @param cid 购物车id
     * @param uid 用户id
     */
    void deleteByCid(Integer cid, Integer uid);

    /**
     * 根据若干个不确定的id值,查询购物车数据
     * @param uid 用户id 判断数据归属
     * @param cids 购物车ids
     * @return 购物车数据集合
     */
    List<CartVO> getVOByCids(Integer uid, Integer[] cids);
}
