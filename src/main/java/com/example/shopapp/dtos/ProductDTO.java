package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.apache.logging.log4j.message.Message;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    @NotEmpty(message = "Product's name is not empty")
    @Size(min = 3, max = 200, message = "Titile must be between 3 and 200 characters")
    private String name;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    @Max(value = 1000000000, message = "Price must be less than or equal to 1,000,000,000")
    private float price;
    private String thumbnail;

    private String description;

    @JsonProperty("category_id") //chú thích do trong db lưu trường này là category_id
    private Long categoryId;
}
