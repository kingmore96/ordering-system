package com.example.demo.converter;

import com.example.demo.dataObjects.OrderDTO;
import com.example.demo.dataObjects.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderMaster 转换为 OrderDTO
 */
public class OrderMaster2OrderDTO {

    /**
     * 单个orderMaster 转换为 orderdto
     * @param orderMaster
     * @return
     */
    public static OrderDTO masterToDTO(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    /**
     * ordermaster 的list 转化为 orderdto的list
     * @param orderMasterList
     * @return
     */
    public static List<OrderDTO> listToList(List<OrderMaster> orderMasterList){
        List<OrderDTO> orderDTOList = orderMasterList.stream().map(e -> {
            OrderDTO orderDTO = masterToDTO(e);
            return orderDTO;
        }).collect(Collectors.toList());
        return orderDTOList;
    }
}
