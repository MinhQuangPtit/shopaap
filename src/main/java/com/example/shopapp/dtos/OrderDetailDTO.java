package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailDTO {
    @JsonProperty("order_id")
    @Min(value = 1, message = "Orders's Id must be > 0")
    private Long orderId;

    @Min(value = 1, message = "Product's ID must be > 0")
    @JsonProperty("product_id")
    private Long productId;

    @Min(value = 0, message = "price must be > 0")
    private Long price;

    @Min(value = 1, message = "price must be > 0")
    @JsonProperty("number_of_products")
    private int numberOfProducts;

    @JsonProperty("total_money")
    private int totalMoney;

    private String color;
}
