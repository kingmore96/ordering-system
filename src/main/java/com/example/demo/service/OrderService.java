package com.example.demo.service;

import com.example.demo.dataObjects.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * 面向买家的订单service相关操作
 */
public interface OrderService {

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 查询单个订单
     * @param orderId
     * @return
     */
    OrderDTO findOne(String orderId);

    /**
     * 根据买家openid查询所有订单
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /**
     * 取消订单（可以用户取消，也可以商家取消，但是这个取消的动作是面向买家的）
     * @param orderDTO
     * @return
     */
    Boolean cancel(OrderDTO orderDTO);

    /**
     * 完结订单
     * @param orderDTO
     * @return
     */
    Boolean finished(OrderDTO orderDTO);


    /**
     * 修改订单状态为已支付
     * @param orderDTO
     * @return
     */
    OrderDTO paid(OrderDTO orderDTO);
}
