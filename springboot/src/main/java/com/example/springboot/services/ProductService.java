package com.example.springboot.services;

import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<ProductModel> getAllProductsService() {
        return productRepository.findAll();
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
