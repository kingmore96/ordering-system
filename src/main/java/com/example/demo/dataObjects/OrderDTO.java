package com.example.demo.dataObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO extends OrderMaster{
    private List<OrderDetail> orderDetailList;
}
