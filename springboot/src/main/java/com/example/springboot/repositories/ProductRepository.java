package com.example.springboot.repositories;

import com.example.springboot.models.ProductModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {

    @Override
    @NotNull
    Page<ProductModel> findAll(@NotNull Pageable pageable);
}
