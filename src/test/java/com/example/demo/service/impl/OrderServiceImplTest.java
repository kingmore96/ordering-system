package com.example.demo.service.impl;

import com.example.demo.DemoApplicationTests;
import com.example.demo.dataObjects.OrderDTO;
import com.example.demo.dataObjects.OrderDetail;
import com.example.demo.service.OrderService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class OrderServiceImplTest extends DemoApplicationTests {

    @Autowired
    private OrderService orderService;

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("青春公社");
        orderDTO.setBuyerName("小任");
        orderDTO.setBuyerOpenid("123123123123");
        orderDTO.setBuyerPhone("13523456789");

        List<OrderDetail> orderDetailList = new LinkedList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("123456");
        o1.setProductQuantity(1);
        orderDetailList.add(o1);
        orderDTO.setOrderDetailList(orderDetailList);

        orderService.create(orderDTO);
    }

    @Test
    public void findOne() {
        String orderId = "15275601166493852404";
        OrderDTO one = orderService.findOne(orderId);
        Assert.assertNotNull(one);
    }

    @Test
    public void findList() {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<OrderDTO> list = orderService.findList("123123123123", pageRequest);
        Assert.assertNotNull(list);
        Assert.assertEquals(1,list.getContent().size());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("15275601166493852404");
        orderDTO.setBuyerOpenid("123123123123");
        orderService.cancel(orderDTO);
    }

    @Test
    public void finished() {
    }

    @Test
    public void paid() {
    }
}