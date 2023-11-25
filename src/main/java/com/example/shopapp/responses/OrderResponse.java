package com.example.shopapp.responses;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class OrderResponse{
    private long id;
    private String fullname;
    private String email;
    private String phoneNumber;
    private String address;
    private String note;
    private LocalDateTime orderDate;
    private String status;
    private float totalMoney;
    private String shippingMethod;
    private String shippingAddress;
    private Date shippingDate;
    private String trackingNumber;
    private String paymentMethod;
    private Boolean active;
    private long userId;
}
