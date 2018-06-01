package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatusEnums {
    NEW(0,"新订单"),
    SUCCESS(1,"已完结"),
    CANCLED(2,"已取消");

    private Integer code;
    private String description;


}
