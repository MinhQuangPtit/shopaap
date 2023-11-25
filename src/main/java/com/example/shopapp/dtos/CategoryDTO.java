package com.example.shopapp.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.apache.logging.log4j.message.Message;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    @NotEmpty(message = "Category's name cannot be empty")
    private String name;
}
