package com.example.demo.controller;

import com.example.demo.dataObjects.OrderDTO;
import com.example.demo.enums.ResultEnums;
import com.example.demo.exception.SellException;
import com.example.demo.service.OrderService;
import com.example.demo.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    /**
     * 发起支付
     * @param orderId
     * @param returnUrl
     */
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String,Object> map){
        //1 查询订单，如果不存在抛异常
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null){
            throw new SellException(ResultEnums.ORDER_NOT_EXIST);
        }
        //2 发起支付,这里会返回给我预支付订单详情，我需要把他传到一个freemaker里面
        PayResponse payResponse = payService.create(orderDTO);

        //3 传参数
        map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);
        return new ModelAndView("pay/create",map);
    }

    /**
     * 修改订单状态，并通知微信，修改好了，不用再发异步通知了
     * @param notifyData
     * @return
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){
        PayResponse notify = payService.notify(notifyData);
        //通知微信，我们修改好了
        return new ModelAndView("pay/success");
    }
}
