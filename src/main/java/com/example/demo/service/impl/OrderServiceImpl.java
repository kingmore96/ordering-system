package com.example.demo.service.impl;

import com.example.demo.converter.OrderMaster2OrderDTO;
import com.example.demo.dataObjects.OrderDTO;
import com.example.demo.dataObjects.OrderDetail;
import com.example.demo.dataObjects.OrderMaster;
import com.example.demo.dataObjects.ProductInfo;
import com.example.demo.enums.OrderStatusEnums;
import com.example.demo.enums.PayStatusEnums;
import com.example.demo.enums.ResultEnums;
import com.example.demo.exception.SellException;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderMasterRepository;
import com.example.demo.service.OrderService;
import com.example.demo.service.PayService;
import com.example.demo.service.ProductInfoService;
import com.example.demo.utils.KeyUtils;
import com.lly835.bestpay.model.RefundResponse;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PayService payService;

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        //总价
        BigDecimal orderAmount = new BigDecimal(0);
        //订单id
        String orderId = KeyUtils.genUniqueKey();
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            //查询商品详细信息
            ProductInfo productInfo = getProductInfo(orderDetail);
            //扣库存
            decreaseStock(orderDetail, productInfo);
            //订单详情入库
            saveOrderDetail(orderId,orderDetail,productInfo);
            //计算总价
            orderAmount = getOrderAmount(orderAmount, orderDetail, productInfo);

        }
        //订单入库
        saveOrderMaster(orderDTO, orderAmount, orderId);
        //返回OrderDTO
        orderDTO.setOrderId(orderId);
        return orderDTO;
    }


    /**
     * 查询订单
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if(orderMaster == null){
            throw new SellException(ResultEnums.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnums.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = OrderMaster2OrderDTO.masterToDTO(orderMaster);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    /**
     * 查询订单列表
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTO.listToList(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,
                orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    /**
     * 取消订单
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public Boolean cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderDTO.getOrderId());
        if(orderMaster == null){
            throw new SellException(ResultEnums.ORDER_NOT_EXIST);
        }

        //先判断订单状态
        if(!orderMaster.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            throw new SellException(ResultEnums.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnums.CANCLED.getCode());
        orderMasterRepository.save(orderMaster);

        //增加库存
        OrderDTO orderDTO1 = findOne(orderDTO.getOrderId());
        increaseStock(orderDTO1);

        //如果是已支付，需要退款
        if(orderMaster.getPayStatus().equals(PayStatusEnums.SUCCESS)){
           payService.refund(orderDTO);
        }
        return true;
    }



    @Override
    public Boolean finished(OrderDTO orderDTO) {
        //判断订单状态
        OrderMaster orderMaster = orderMasterRepository.findOne(orderDTO.getOrderId());
        if (orderMaster == null) {
            throw new SellException(ResultEnums.ORDER_NOT_EXIST);
        }
        //如果是已取消，抛异常
        if(!orderMaster.getOrderStatus().equals(OrderStatusEnums.NEW)){
            throw new SellException(ResultEnums.ORDER_STATUS_ERROR);
        }
        //修改状态
        orderMaster.setOrderStatus(OrderStatusEnums.SUCCESS.getCode());
        orderMasterRepository.save(orderMaster);
        log.info("【修改订单状态】",orderMaster.getOrderStatus());
        return true;
    }

    /**
     * 修改订单状态为已支付
     * @param orderDTO
     * @return
     */
    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderDTO.getOrderId());
        if(orderMaster == null){
            throw new SellException(ResultEnums.ORDER_NOT_EXIST);
        }
        //判断订单状态
        if(!orderMaster.getOrderStatus().equals(OrderStatusEnums.NEW)){
            throw new SellException(ResultEnums.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!orderMaster.getPayStatus().equals(PayStatusEnums.WAIT)){
            throw new SellException(ResultEnums.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderMaster.setPayStatus(PayStatusEnums.SUCCESS.getCode());
        OrderMaster save = orderMasterRepository.save(orderMaster);
        orderDTO.setPayStatus(PayStatusEnums.SUCCESS.getCode());
        log.info("【修改支付状态】：{}",save);
        return orderDTO;
    }

    /**
     * 计算总价
     * @param orderAmount
     * @param orderDetail
     * @param productInfo
     * @return
     */
    private BigDecimal getOrderAmount(BigDecimal orderAmount, OrderDetail orderDetail, ProductInfo productInfo) {
        //计算总价
        orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
                .add(orderAmount);
        return orderAmount;
    }

    /**
     * 查询商品信息
     * @param orderDetail
     * @return
     */
    private ProductInfo getProductInfo(OrderDetail orderDetail) {
        ProductInfo productInfo = productInfoService.findById(orderDetail.getProductId());
        //如果查询商品不存在，抛出异常
        if(productInfo == null){
            throw new SellException(ResultEnums.PRODUCT_NOT_EXIST);
        }
        return productInfo;
    }

    /**
     * 扣库存
     * @param orderDetail
     * @param productInfo
     */
    private void decreaseStock(OrderDetail orderDetail, ProductInfo productInfo) {
        //判断库存
        if(productInfo.getProductStock() - orderDetail.getProductQuantity() < 0){
            throw new SellException(ResultEnums.PRODUCT_STOCK_ERROR);
        }

        //扣库存
        productInfo.setProductStock(productInfo.getProductStock() - orderDetail.getProductQuantity());

        //把新商品详情，存入数据库
        productInfoService.save(productInfo);
    }

    /**
     * 订单详情入库
     * @param orderId
     * @param productInfo
     */
    private void saveOrderDetail(String orderId, OrderDetail orderDetail,ProductInfo productInfo) {
        //生成订单详情
        OrderDetail newOrderDetail = new OrderDetail();
        BeanUtils.copyProperties(productInfo,newOrderDetail);
        newOrderDetail.setProductQuantity(orderDetail.getProductQuantity());
        newOrderDetail.setDetailId(KeyUtils.genUniqueKey());
        newOrderDetail.setOrderId(orderId);
        //入库
        orderDetailRepository.save(newOrderDetail);
    }

    /**
     * 订单入库
     * @param orderDTO
     * @param orderAmount
     * @param orderId
     */
    private void saveOrderMaster(OrderDTO orderDTO, BigDecimal orderAmount, String orderId) {
        //生成新订单
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnums.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnums.WAIT.getCode());
        //订单入库
        orderMasterRepository.save(orderMaster);
    }

    /**
     * 增加库存
     * @param orderDTO1
     */
    private void increaseStock(OrderDTO orderDTO1) {
        for (OrderDetail orderDetail : orderDTO1.getOrderDetailList()) {
            ProductInfo productInfo = productInfoService.findById(orderDetail.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnums.PRODUCT_NOT_EXIST);
            }
            productInfo.setProductStock(productInfo.getProductStock() + orderDetail.getProductQuantity());
            productInfoService.save(productInfo);
        }

    }
}
