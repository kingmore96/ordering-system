package com.example.demo.repository;

import com.example.demo.DemoApplicationTests;
import com.example.demo.dataObjects.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.math.BigDecimal;


public class OrderMasterRepositoryTest extends DemoApplicationTests {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerAddress("青春公社");
        orderMaster.setBuyerName("网耿耿");
        orderMaster.setBuyerOpenid("1234");
        orderMaster.setBuyerPhone("12345678912");
        orderMaster.setOrderAmount(new BigDecimal(2.5));
        orderMaster.setOrderId("123123");

        OrderMaster save = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(save);
    }

    @Test
    public void findByBuyerOpenid() {
        PageRequest pageRequest = new PageRequest(0, 1);
        Page<OrderMaster> byBuyerOpenid = orderMasterRepository.findByBuyerOpenid("1234", pageRequest);

        Assert.assertEquals(1,byBuyerOpenid.getContent().size());
    }
}