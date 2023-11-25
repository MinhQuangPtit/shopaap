package com.example.shopapp.controller;


import com.example.shopapp.dtos.OrderDetailDTO;
import com.example.shopapp.service.OrderDetailService;
import com.example.shopapp.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order_details")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @PostMapping("")
    public ResponseEntity<?> createOrderDetail (
            @Valid @RequestBody OrderDetailDTO orderDetailDTO,
            BindingResult result){
        try {
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            return ResponseEntity.ok(orderDetailService.createOrderDetail(orderDetailDTO));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDettail( @Valid @PathVariable Long id ){
        return ResponseEntity.ok(orderDetailService.getOrderDetail(id));
    }

    //  Lấy danh sách các orderDetail của 1 order
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable("orderId") Long orderId ){
        return ResponseEntity.ok(orderDetailService.getOrderDetails(orderId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailDTO orderDetailDTO){
        return ResponseEntity.ok(orderDetailService.updateOrderDetail(id,orderDetailDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail( @Valid @PathVariable("id") Long id ){
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.noContent().build();
    }
}
