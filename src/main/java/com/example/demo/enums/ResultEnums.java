package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 返回结果枚举
 */
@Getter
@AllArgsConstructor
public enum ResultEnums {
    SUCCESS(0,"成功"),
    FAIL(1,"失败"),
    FORM_PARAM_ERROR(7,"表单校验失败"),
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"商品库存不足"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDERDETAIL_NOT_EXIST(13,"订单详情不存在"),
    ORDER_STATUS_ERROR(14,"订单状态异常"),
    ORDER_OWNER_ERROR(15,"该订单不属于当前用户"),
    WX_MP_ERROR(16,"微信公众号异常"),
    WX_PAY_ORDERAMOUNT_ERROR(17,"微信支付金额和订单不一致"),
    ORDER_PAY_STATUS_ERROR(18,"订单支付状态异常")
    ;

    private Integer code;
    private String msg;


}
