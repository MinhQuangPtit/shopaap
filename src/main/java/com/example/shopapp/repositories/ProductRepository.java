package com.example.shopapp.repositories;


import com.example.shopapp.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsByName(String name);

    //Page<Product> findAll(Pageable pageable);
}

