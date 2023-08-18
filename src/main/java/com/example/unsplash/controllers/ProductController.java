package com.example.unsplash.controllers;

import com.example.unsplash.models.Product;
import com.example.unsplash.models.ResponseObject;
import com.example.unsplash.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RestController
@RequestMapping(path = "api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public ResponseEntity<ResponseObject> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        String message = products.isEmpty() ? "there is no any product" : "all products";
        return ResponseEntity.ok(new ResponseObject(HttpStatus.OK.value(), message, products));

    }

    @PostMapping
    public ResponseEntity<ResponseObject> registerNewProduct(@RequestBody Product product) {
        productService.addProduct(product);
        return ResponseEntity.ok(new ResponseObject(200,
                "registered a product successfully",
                product));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseObject> getProduct(@PathVariable("id") Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(new ResponseObject(200, "Product found", product));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        }
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<ResponseObject> updateProduct(
            @PathVariable("id") Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) double price) {
        try {
            Product product = productService.updateProductById(id, name, description, price);
            return ResponseEntity.ok(new ResponseObject(200, "Update product successfully!", product));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        }
    }

}
