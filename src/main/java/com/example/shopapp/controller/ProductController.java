package com.example.shopapp.controller;

import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.dtos.ProductImageDTO;
import com.example.shopapp.models.Product;
import com.example.shopapp.models.ProductImage;
import com.example.shopapp.responses.ProductListResponse;
import com.example.shopapp.responses.ProductResponse;
import com.example.shopapp.service.ProductService;
import com.github.javafaker.Faker;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("")
    public ResponseEntity<ProductListResponse> getProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit){
        PageRequest pageRequest = PageRequest.of(page,limit, Sort.by("createdAt").descending());
        Page<ProductResponse> productPage =  productService.getAllProduct(pageRequest);
        List<ProductResponse> products = productPage.getContent();
        long totalProduct = productPage.getTotalElements();
        long totalPage = productPage.getTotalPages();
        long pageNumber = productPage.getPageable().getPageNumber();
        long productOfPage = productPage.getContent().size();
        return  ResponseEntity.ok(ProductListResponse.builder()
                .productResponseList(products)
                .totalProduct(totalProduct)
                .pageNumber(pageNumber)
                .totalPage(totalPage)
                .productOfPage(productOfPage)
                .build()
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping(value = "")
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody ProductDTO productDTO,
            BindingResult result){
        try {
            if(result.hasErrors()){
                // valid
                List<String> errorsMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorsMessage);

            }
            Product newProduct =  productService.createProduct(productDTO);
            return  ResponseEntity.ok(newProduct);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "upload/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @PathVariable Long id,
            @ModelAttribute("files") List<MultipartFile> files){
        try {
            Product existingProduct = productService.getProductById(id);
            files = files == null ? new ArrayList<MultipartFile>() : files;
            List<ProductImage> productImageList = new ArrayList<>();
            for(MultipartFile file : files){
                if(file.getSize() == 0){
                    continue;
                }
                // Kiểm tra kích thước file và định dạng
                if(file.getSize() > 10 *1024 *1024 ) { //Kích thước > 10mb
                    throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE,"File is too large! Maximum size is 10Mb");
                }
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")){
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
                // Lưu file và cập nhật thumbnail trong DTO
                String filename = storeFile(file);
                ProductImage productImage = productService.createProductImage(
                        existingProduct.getId(),
                        new ProductImageDTO()
                                .builder()
                                .productId(existingProduct.getId())
                                .imageUrl(filename)
                                .build()
                );
                productImageList.add(productImage);
            }
            return ResponseEntity.ok(productImageList);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException{
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        //Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" +filename;

        //Đường dẫn đến thư mục mà bạn muốn lưu file
        java.nio.file.Path uploadDir = Paths.get("uploads");

        //Kiểm tra và tạo thư mục nếu nó không tồn tại
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        //Đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        //Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO productDTO){
        productService.updateProduct(id,productDTO);
        return ResponseEntity.ok("Update product successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable long id
    ){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Deleted product");
    }

    @PostMapping("/generateFakeProducts")
    public ResponseEntity<String> generateFakeProducts(){
        Faker faker = new Faker();
        for(int i = 0 ; i <10000;i++){
            String productName = faker.commerce().productName();

            ProductDTO productDTO = new ProductDTO().builder()
                    .name(productName)
                    .price(faker.number().numberBetween(10,100000))
                    .description(faker.lorem().sentence())
                    .categoryId((long)faker.number().numberBetween(3,6))
                    .build();
            try {
                productService.createProduct(productDTO);
            }
            catch (Exception e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.ok("Fake product created successfully");
    }

}
