package com.example.demo.service;

import com.example.demo.dataObjects.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    /**
     * 查询所有类目，因为对于我们的项目来说，类目不多，所以不需要分页了
     * @return
     */
    List<ProductCategory> findAll();

    /**
     * 根据类目id查询类目
     * @param categoryId
     * @return
     */
    ProductCategory findById(Integer categoryId);

    /**
     * 增加类目，更新类目（Id是否为空）
     * @param productCategory
     * @return
     */
    ProductCategory save(ProductCategory productCategory);

    /**
     * 根据类目编号列表，查询类目列表
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

}
