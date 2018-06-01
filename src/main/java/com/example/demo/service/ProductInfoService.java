package com.example.demo.service;

import com.example.demo.dataObjects.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {
    /**
     * 查询所有，要分页，所以要传一个分页参数
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 查询所有在架商品
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 根据id查询商品信息
     * @param productId
     * @return
     */
    ProductInfo findById(String productId);

    /**
     * 新增或更新商品信息
     * @param productInfo
     * @return
     */
    ProductInfo save(ProductInfo productInfo);
}
