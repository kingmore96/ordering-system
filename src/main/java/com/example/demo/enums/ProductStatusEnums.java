package com.example.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatusEnums {
    UP(0,"上架"),
    DOWN(1,"下架")
    ;

    private Integer code;
    private String description;


}
