package com.example.demo.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 第二层数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVO {
    /**
     * 类目名称
     */
    @JsonProperty("name")
    private String categoryName;

    /**
     * 类目编号
     */
    @JsonProperty("type")
    private Integer categoryType;

    /**
     * 商品信息列表
     */
    @JsonProperty("foods")
    List<ProductInfoVO> productInfoVOList;

    
}
