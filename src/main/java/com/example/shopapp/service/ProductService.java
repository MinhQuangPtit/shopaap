package com.example.shopapp.service;

import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.dtos.ProductImageDTO;
import com.example.shopapp.models.Category;
import com.example.shopapp.models.Product;
import com.example.shopapp.models.ProductImage;
import com.example.shopapp.repositories.CategoryRepository;
import com.example.shopapp.repositories.ProductImageRepository;
import com.example.shopapp.repositories.ProductRepository;
import com.example.shopapp.responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    @Override
    public Product createProduct(ProductDTO productDTO){
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow();
        Product product =
                new Product().builder()
                        .name(productDTO.getName())
                        .price(productDTO.getPrice())
                        .description(productDTO.getDescription())
                        .category(category)
                        .build();
        return productRepository.save(product);
    }

    @Override
    public Page<ProductResponse> getAllProduct(PageRequest pageRequest){
        return productRepository.findAll(pageRequest).map(
                product -> ProductResponse.transformProduct(product)
        );
    }

    @Override
    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found"));
    }

    @Override
    public Product updateProduct(Long productId,ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow();
        Product existingProduct = getProductById(productId);
        existingProduct.setCategory(category);
        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setThumbnail(productDTO.getThumbnail());
        productRepository.save(existingProduct);
        return existingProduct;
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.delete(product);
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO){
        Product existingProduct = productRepository.findById(productId).orElseThrow();
        ProductImage productImage = new ProductImage()
                .builder()
                .productId(productId)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        // Không cho insert quá 5 ảnh trong 1 sp
        if(productImageRepository.findAllByProductId(productId).size() > ProductImage.MAXIMUM_IMAGES ){
            throw new RuntimeException("Maximum is + "+ ProductImage.MAXIMUM_IMAGES +" images");
        };
        return productImageRepository.save(productImage);
    }
}
