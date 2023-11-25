package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDTO {
    @JsonProperty("user_id")
    @Min(value = 1,message = "User's Id must be > 0")
    private long userId;

    @JsonProperty("fullname")
    private String fullName;

    private String email;

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private String address;

    private String note;

    @JsonProperty("order_date")
    private Date orderDate;

    @JsonProperty("total_money")
    @Min(value = 0,message = "Total money must be >= 0")
    private float totalMoney;

    @JsonProperty("shipping_date")
    private Date shippingDate;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @NotBlank(message = "Address shipping is required")
    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("payment_method")
    private String paymentMethod;

}
