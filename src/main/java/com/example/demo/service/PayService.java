package com.example.demo.service;

import com.example.demo.dataObjects.OrderDTO;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

/**
 * 支付服务接口
 */
public interface PayService {

    /**
     * 发起支付
     * @param orderDTO
     */
    PayResponse create(OrderDTO orderDTO);

    /**
     * 异步通知商户，商户根据此notifyData，修改用户的支付状态等信息
     * @param notifyData
     * @return
     */
    PayResponse notify(String notifyData);

    /**
     * 退款
     * @param orderDTO
     * @return
     */
    RefundResponse refund(OrderDTO orderDTO);
}
