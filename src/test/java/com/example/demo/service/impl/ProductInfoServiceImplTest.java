package com.example.demo.service.impl;

import com.example.demo.DemoApplicationTests;
import com.example.demo.dataObjects.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class ProductInfoServiceImplTest extends DemoApplicationTests {
    @Autowired
    private ProductInfoServiceImpl productInfoServiceImpl;

    @Test
    public void findAll() {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<ProductInfo> all = productInfoServiceImpl.findAll(pageRequest);
        Assert.assertNotEquals(0,all.getTotalElements());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> upAll = productInfoServiceImpl.findUpAll();
        Assert.assertNotNull(upAll);
        Assert.assertNotEquals(0,upAll.size());
    }

    @Test
    public void findById() {
        ProductInfo byId = productInfoServiceImpl.findById("123456");
        Assert.assertNotNull(byId);
    }

    @Test
    public void save() {
        ProductInfo save = productInfoServiceImpl.save(new ProductInfo("1234568756", "炒鸡蛋", new BigDecimal(0.2), 10, "西红柿炒蛋", 0, "http://xxx.com", 3));
        Assert.assertNotNull(save);
    }
}