package com.example.shopapp.service;

import com.example.shopapp.dtos.OrderDetailDTO;
import com.example.shopapp.models.OrderDetail;
import com.example.shopapp.responses.OrderDetailResponse;

import java.util.List;

public interface IOrderDetailService {
    OrderDetailResponse createOrderDetail(OrderDetailDTO orderDetailDTO);
    OrderDetailResponse getOrderDetail(long id);
    OrderDetailResponse updateOrderDetail(long id, OrderDetailDTO orderDetailDTO);
    void deleteOrderDetail(long id);
    List<OrderDetail> getOrderDetails(long orderId);
}
