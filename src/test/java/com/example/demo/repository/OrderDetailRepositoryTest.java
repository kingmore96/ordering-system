package com.example.demo.repository;

import com.example.demo.DemoApplicationTests;
import com.example.demo.dataObjects.OrderDetail;
import lombok.AllArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class OrderDetailRepositoryTest extends DemoApplicationTests {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123123");
        orderDetail.setOrderId("123123");
        orderDetail.setProductIcon("http://ddxd.com");
        orderDetail.setProductId("123456");
        orderDetail.setProductName("皮蛋瘦肉粥lalala");
        orderDetail.setProductPrice(new BigDecimal(2.5));
        orderDetail.setProductQuantity(3);

        OrderDetail save = orderDetailRepository.save(orderDetail);
        Assert.assertNotNull(save);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> byOrderId = orderDetailRepository.findByOrderId("123123");
        Assert.assertEquals(1,byOrderId.size());

    }
}