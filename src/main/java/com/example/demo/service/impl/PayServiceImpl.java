package com.example.demo.service.impl;

import com.example.demo.dataObjects.OrderDTO;
import com.example.demo.enums.ResultEnums;
import com.example.demo.exception.SellException;
import com.example.demo.service.OrderService;
import com.example.demo.service.PayService;
import com.example.demo.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BestPayServiceImpl bestPayService;

    private static final String ORDER_NAME = "微信支付";

    /**
     * 发起支付，生成预付订单信息，转送给客户，让其支付
     * @param orderDTO
     * @return
     */
    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付参数】payRequest：{}",payRequest);
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付结果】payResponse：{}",payResponse);
        return payResponse;
    }

    /**
     * 支付后，微信发过来通知，我们修改支付状态
     * @param notifyData
     * @return
     */
    @Override
    public PayResponse notify(String notifyData) {
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);

        //查询订单
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
        if (orderDTO == null) {
            throw new SellException(ResultEnums.ORDER_NOT_EXIST);
        }

        //判断金额
        if(!MathUtil.equals(payResponse.getOrderAmount(),orderDTO.getOrderAmount().doubleValue())){
            throw new SellException(ResultEnums.WX_PAY_ORDERAMOUNT_ERROR);
        }

        OrderDTO result = orderService.paid(orderDTO);
        log.info("【异步通知 修改支付状态】：{}",result);
        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】",refundResponse);
        return refundResponse;
    }
}
