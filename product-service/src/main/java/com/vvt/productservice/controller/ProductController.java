package com.vvt.productservice.controller;

import com.vvt.productservice.dto.ProductRequest;
import com.vvt.productservice.dto.ProductResponse;
import com.vvt.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllProduct(){
        List<ProductResponse> list = productService.getAllProduct();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
