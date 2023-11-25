package com.example.shopapp.service;

import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.dtos.ProductImageDTO;
import com.example.shopapp.models.Product;
import com.example.shopapp.models.ProductImage;
import com.example.shopapp.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IProductService {
    public Product createProduct(ProductDTO productDTO);
    public Page<ProductResponse> getAllProduct(PageRequest pageRequest);
    public Product getProductById(Long id);
    public Product updateProduct(Long productId,ProductDTO productDTO);
    public void deleteProduct(Long id);
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO);
}
