package com.hzh.computer_store.service.impl;

import com.hzh.computer_store.entity.Cart;
import com.hzh.computer_store.entity.Product;
import com.hzh.computer_store.mapper.CartMapper;
import com.hzh.computer_store.mapper.ProductMapper;
import com.hzh.computer_store.service.ICartService;
import com.hzh.computer_store.service.ex.*;
import com.hzh.computer_store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/** 添加到购物车业务层实现类 */
@Service
public class CartServiceImpl implements ICartService {

    /**购物车的业务层依赖于购物车的持久层以及商品的持久层*/
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    //添加商品到购物车
    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
        //查询购物车中该商品是否已存在
        Cart result = cartMapper.findByUidAndPid(uid, pid);

        Date date = new Date();

        //如果没有购物车|购物车中没有该商品,则新增购物车对象加入该商品|加入该商品
        if (result == null) {
            Cart cart = new Cart();
            //封装数据
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);

            //查询商品数据,获取商品价格并封装
            Product product = productMapper.findById(pid);
            cart.setPrice(product.getPrice());

            //封装日志
            cart.setCreatedUser(username);
            cart.setCreatedTime(date);
            cart.setModifiedUser(username);
            cart.setModifiedTime(date);

            //插入数据
            Integer rows = cartMapper.insert(cart);
            if (rows != 1) {
                throw new InsertException("插入数据时出现未知异常");
            }
        } else { //如果购物车已有该商品,则更新商品数量
            //计算商品数量 原有+新增加的 商品加入购物车不会出现减的情况
            Integer num = result.getNum() + amount;
            //获取已有的购物车cid
            Integer cid = result.getCid();
            //更新该购物车商品数量 num
            Integer rows = cartMapper.updateNumByCid(cid, num, username, date);
            if (rows != 1) {
                throw new InsertException("插入数据时出现未知异常");
            }
        }
    }

    //查询某用户的购物车数据
    @Override
    public List<CartVO> getVOByUid(Integer uid) {
        return cartMapper.findVOByUid(uid);
    }

    //增加用户的购物车中某商品的数量
    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        //查询购物车数据是否存在和数据归属
        Cart result = cartMapper.findByCid(cid);
        if (result == null) {
            throw new CartNotFoundException("数据不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("数据非法访问");
        }

        //增加商品数量 点一次增加按钮,发送一次请求,调用一次addNum,增加一个数量
        Integer num = result.getNum() + 1;
        Integer rows = cartMapper.updateNumByCid(cid, num, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知异常");
        }
        return num;
    }

    //减少用户的购物车中某商品的数量
    @Override
    public Integer subNum(Integer cid, Integer uid, String username) {
        //查询购物车数据是否存在和数据归属
        Cart result = cartMapper.findByCid(cid);
        if (result == null) {
            throw new CartNotFoundException("数据不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("数据非法访问");
        }

        //减少商品数量 点一次减少按钮,发送一次请求,调用一次addNum,减少一个数量
        Integer num = result.getNum() - 1;
        Integer rows = cartMapper.updateNumByCid(cid, num, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知异常");
        }
        return num;
    }

    //删除购物车中的某商品
    @Override
    public void deleteByCid(Integer cid, Integer uid) {
        //查询购物车数据是否存在和数据归属
        Cart result = cartMapper.findByCid(cid);
        if (result == null) {
            throw new CartNotFoundException("数据不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("数据非法访问");
        }

        //删除商品
        Integer rows = cartMapper.deleteByCid(cid, uid);
        if (rows != 1) {
            throw new DeleteException("删除数据时产生未知异常");
        }
    }

    //查询多个购物车的数据
    @Override
    public List<CartVO> getVOByCids(Integer uid, Integer[] cids) {
        List<CartVO> cartVOList = cartMapper.findVOByCids(cids);
        Iterator<CartVO> iterator = cartVOList.iterator();
        while (iterator.hasNext()) {
            //指向的是该元素之前,所以需要next得到该元素
            CartVO cartVO = iterator.next();
            //如果数据归属错误,删除即可,无需抛出异常
            if (!cartVO.getUid().equals(uid)) {
                //不能用list.remove(cart)
                //在迭代器进行遍历的时候不能使用集合的移除方法,需要用迭代器特有的移除方法
                iterator.remove();
            }
        }
        return cartVOList;
    }
}
