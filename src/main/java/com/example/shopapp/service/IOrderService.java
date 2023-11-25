package com.example.shopapp.service;

import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.models.Order;
import com.example.shopapp.responses.OrderResponse;
import org.hibernate.exception.DataException;

import java.util.List;

public interface IOrderService {
    OrderResponse createOrder(OrderDTO orderDTO) throws DataException;
    OrderResponse getOrderById(long id);
    OrderResponse updateOrder(long id, OrderDTO orderDTO);
    List<OrderResponse> findByUserId(long userId);
    void deleteOrder(long id);
}
