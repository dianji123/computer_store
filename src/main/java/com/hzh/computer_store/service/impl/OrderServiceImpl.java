package com.hzh.computer_store.service.impl;

import com.hzh.computer_store.entity.Address;
import com.hzh.computer_store.entity.Order;
import com.hzh.computer_store.entity.OrderItem;
import com.hzh.computer_store.mapper.OrderMapper;
import com.hzh.computer_store.service.IAddressService;
import com.hzh.computer_store.service.ICartService;
import com.hzh.computer_store.service.IOrderService;
import com.hzh.computer_store.service.IUserService;
import com.hzh.computer_store.service.ex.InsertException;
import com.hzh.computer_store.vo.CartVO;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/** 创建订单业务层实现类 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    //需要调用地址业务层的getByAid方法
    @Autowired
    private IAddressService addressService;

    //需要调用购物车业务层的getVOByCids方法
    @Autowired
    private ICartService cartService;

    //需要调用户用业务层的getByUid方法
    private IUserService userService;

    //创建订单
    @Override
    public Order create(Integer aid, Integer[] cids, Integer uid, String username) {
        //返回的列表中的对象都是即将下单的
        List<CartVO> list = cartService.getVOByCids(uid, cids);

        //计算总金额
        long totalPrice = 0L;
        for (CartVO cartVO: list) {
            totalPrice += cartVO.getRealPrice()*cartVO.getNum();
        }

        //获取收货地址
        Address address = addressService.getByAid(aid, uid);
        //创建订单对象
        Order order = new Order();
        //封装订单数据

        order.setUid(uid);

        //封装收货地址
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());

        //封装创建时间,支付状态和总价
        order.setOrderTime(new Date());
        order.setStatus(0);
        order.setTotalPrice(totalPrice);

        //封装日志
        order.setCreatedUser(username);
        order.setCreatedTime(new Date());
        order.setModifiedUser(username);
        order.setModifiedTime(new Date());

        //创建订单 t_order创建一条订单数据
        Integer rows = orderMapper.insertOrder(order);
        if (rows != 1) {
            throw new InsertException("插入数据时产生未知的异常");
        }

        //订单数据关联订单——将购买的商品数据插入到t_order_item
        for (CartVO cartVO: list) {
            OrderItem orderItem = new OrderItem();
            /**
             * 此时获取的oid不为空,因为在配置文件里面开启了oid主
             * 键自增,所以上面的代码执行插入时就自动将oid赋值了
             */
            orderItem.setOid(order.getOid());

            //封装商品信息
            orderItem.setPid(cartVO.getPid());
            orderItem.setTitle(cartVO.getTitle());
            orderItem.setImage(cartVO.getImage());
            orderItem.setPrice(cartVO.getRealPrice());
            orderItem.setNum(cartVO.getNum());

            //封装日志
            orderItem.setCreatedUser(username);
            orderItem.setCreatedTime(new Date());
            orderItem.setModifiedUser(username);
            orderItem.setModifiedTime(new Date());

            rows = orderMapper.insertOrderItem(orderItem);
            if (rows != 1) {
                throw new InsertException("插入数据时产生未知的异常");
            }
        }

        return order;
    }
}
