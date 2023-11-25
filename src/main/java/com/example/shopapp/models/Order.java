package com.example.shopapp.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "orders")
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname", nullable = false, length = 100)
    private String fullname;

    @NotNull
    private String email;

    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    private String address;

    private String note;

    @Column(name = "order_date ")
    private Date orderDate;

    private String status;

    @Column(name = "total_money")
    private float totalMoney;

    @Column(name = "shipping_method")
    private String shippingMethod;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "shipping_date")
    private Date shippingDate;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;
}
