package com.example.demo.controller;

import com.example.demo.VO.ResultVO;
import com.example.demo.converter.OrderForm2OrderDTO;
import com.example.demo.dataObjects.OrderDTO;
import com.example.demo.enums.ResultEnums;
import com.example.demo.exception.SellException;
import com.example.demo.form.OrderForm;
import com.example.demo.service.OrderService;
import com.example.demo.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new SellException(ResultEnums.FORM_PARAM_ERROR.getCode(),ResultEnums.FORM_PARAM_ERROR.getMsg());
        }
        OrderDTO orderDTO = OrderForm2OrderDTO.covert(orderForm);
        OrderDTO result = orderService.create(orderDTO);

        Map<String,String> map = new HashMap<>();
        map.put("orderId",result.getOrderId());
        return ResultVOUtil.success(map);
    }

    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size){
        if(StringUtils.isEmpty(openid)){
            throw new SellException(ResultEnums.FORM_PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageRequest);
        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){
        if(StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)){
            throw new SellException(ResultEnums.FORM_PARAM_ERROR);
        }

        OrderDTO result = orderService.findOne(orderId);

        if(!result.getBuyerOpenid().equalsIgnoreCase(openid)){
            throw new SellException(ResultEnums.ORDER_OWNER_ERROR);
        }
        return ResultVOUtil.success(result);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){
        if(StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)){
            throw new SellException(ResultEnums.FORM_PARAM_ERROR);
        }

        OrderDTO orderDTO = orderService.findOne(orderId);
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            throw new SellException(ResultEnums.ORDER_OWNER_ERROR);
        }
        Boolean isCancel = orderService.cancel(orderDTO);
        return ResultVOUtil.success(isCancel);
    }

}
