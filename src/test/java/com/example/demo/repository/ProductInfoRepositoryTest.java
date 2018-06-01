package com.example.demo.repository;

import com.example.demo.DemoApplicationTests;
import com.example.demo.dataObjects.ProductInfo;
import com.example.demo.service.ProductInfoService;
import lombok.AllArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ProductInfoRepositoryTest extends DemoApplicationTests {
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    public void findByProductStatus() {
        List<ProductInfo> byProductStatus = productInfoRepository.findByProductStatus(0);
        Assert.assertEquals(1,byProductStatus.size());
    }

    @Test
    public void save(){
        ProductInfo save = productInfoRepository.save(new ProductInfo("123456", "皮蛋瘦肉粥", new BigDecimal(0.2), 10, "好喝的皮蛋粥", 0, "http://xxx.com", 3));
        Assert.assertNotNull(save);
    }

    @Test
    public void update(){
        ProductInfo save = productInfoRepository.save(new ProductInfo("123456", "皮蛋瘦肉粥lalala", new BigDecimal(0.2), 10, "好喝的皮蛋粥", 0, "http://xxx.com", 3));
        Assert.assertNotNull(save);
    }

    @Test
    public void findAll(){
        List<ProductInfo> all = productInfoRepository.findAll();
        Assert.assertNotNull(all);
        Assert.assertEquals(1,all.size());
    }

}