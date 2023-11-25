package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageDTO {
    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("image_url")
    @Size(min = 5,max = 200, message = "Image's name")
    private String imageUrl;
}
