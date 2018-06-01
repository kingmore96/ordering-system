package com.example.demo.repository;

import com.example.demo.dataObjects.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    /**
     * 根据openid查询订单
     * @param openId
     * @param pageable
     * @return
     */
    Page<OrderMaster> findByBuyerOpenid(String openId, Pageable pageable);
}
