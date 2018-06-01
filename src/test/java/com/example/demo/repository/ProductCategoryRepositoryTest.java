package com.example.demo.repository;

import com.example.demo.DemoApplicationTests;
import com.example.demo.dataObjects.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ProductCategoryRepositoryTest extends DemoApplicationTests {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> byCategoryTypeIn = repository.findByCategoryTypeIn(Arrays.asList(2, 3));
        Assert.assertEquals(2,byCategoryTypeIn.size());
    }

    @Test
    public void save(){
        ProductCategory productCategory = repository.save(
                new ProductCategory("男生最爱", 5));
        Assert.assertNotNull(productCategory);
    }

    @Test
    public void update(){
        ProductCategory productCategory = repository.save(
                new ProductCategory(1, "热榜", 4));
        Assert.assertNotNull(productCategory);
    }

    @Test
    public void delete(){
        repository.delete(1);
    }

    @Test
    public void findById(){
        ProductCategory one = repository.findOne(2);
        Assert.assertNotNull(one);
    }

    @Test
    public void testAll(){
        List<ProductCategory> all = repository.findAll();
        Assert.assertNotEquals(0,all.size());
    }
}