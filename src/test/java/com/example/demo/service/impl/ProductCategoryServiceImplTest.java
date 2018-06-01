package com.example.demo.service.impl;

import com.example.demo.DemoApplicationTests;
import com.example.demo.dataObjects.ProductCategory;
import com.example.demo.service.ProductCategoryService;
import oracle.jrockit.jfr.ActiveSettingEvent;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ProductCategoryServiceImplTest extends DemoApplicationTests {
    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    public void findAll() {
        List<ProductCategory> all = productCategoryService.findAll();
        Assert.assertNotNull(all);
        Assert.assertNotEquals(0,all.size());
    }

    @Test
    public void findById() {
        ProductCategory byId = productCategoryService.findById(2);
        Assert.assertNotNull(byId);
    }

    @Test
    public void save() {
        ProductCategory 拉拉爱吃 = productCategoryService.save(new ProductCategory("拉拉爱吃", 8));
        Assert.assertNotNull(拉拉爱吃);
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> byCategoryTypeIn = productCategoryService.findByCategoryTypeIn(Arrays.asList(3, 5));
        Assert.assertNotNull(byCategoryTypeIn);
        Assert.assertEquals(2,byCategoryTypeIn.size());
    }
}