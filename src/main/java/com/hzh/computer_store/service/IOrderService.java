package com.hzh.computer_store.service;

import com.hzh.computer_store.entity.Order;

/**创建订单业务层接口*/
public interface IOrderService {

    /**
     *
     * @param aid 地址
     * @param cids 买的所有商品的购物车id
     * @param uid  用户id
     * @param username 执行人
     * @return 订单
     */
    Order create(Integer aid, Integer[] cids, Integer uid, String username);

}
