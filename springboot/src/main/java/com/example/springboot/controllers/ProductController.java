package com.example.springboot.controllers;


import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import static   org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static   org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productsList = productRepository.findAll();

        if (!productsList.isEmpty()) {
            for (ProductModel product : productsList) {
                UUID id = product.getId();
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(productsList);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> object0 = productRepository.findById(id);

        if (object0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Object not found!");
        }
        object0.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products list"));
        return ResponseEntity.status(HttpStatus.OK).body(object0.get());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid
                                                      ProductRecordDto productRecordDto) {
        Optional<ProductModel> object0 = productRepository.findById(id);
        if (object0.isPresent()) {
            var productModel = object0.get();
            BeanUtils.copyProperties(productRecordDto, productModel);
            return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Object ID doesn't exists!");
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<Object> deleteObject(@PathVariable(value = "id") UUID id) {

        Optional<ProductModel> object0 = productRepository.findById(id);
        if (object0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Object ID doesn't exists!");
        }
        productRepository.delete(object0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Object has Successfully deleted");
    }
}
