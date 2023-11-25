package com.example.shopapp.repositories;

import com.example.shopapp.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    //Tìm các các orderDetail của 1 order
    List<OrderDetail> findByOrderId(Long orderId);
}