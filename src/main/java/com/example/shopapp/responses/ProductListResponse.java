package com.example.shopapp.responses;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductListResponse {
    private List<ProductResponse> productResponseList;
    private long totalProduct;
    private long totalPage;
    private long pageNumber;
    private long productOfPage;
}
