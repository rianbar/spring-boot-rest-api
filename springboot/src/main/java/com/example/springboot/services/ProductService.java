package com.example.springboot.services;

import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Page<ProductModel> getAllProductsService() {
        Pageable firstPageWithFiveElements = PageRequest.of(0, 5, Sort.by("name"));
        return productRepository.findAll(firstPageWithFiveElements);
    }

    @Transactional
    public ProductModel  saveProductService(ProductModel productModel) {
        return productRepository.save(productModel);
    }

    public Optional<ProductModel> getOneProductService(UUID id) {
        Optional<ProductModel> product0;
        product0 = productRepository.findById(id);
        return product0;
    }

    public void deleteProductService(ProductModel object) {
        productRepository.delete(object);
    }
}
