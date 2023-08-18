package com.example.unsplash.services;

import com.example.unsplash.models.Product;
import com.example.unsplash.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Product with id " + id + " not found"));
    }

    @Transactional
    public Product updateProductById(Long id, String name, String description, Double price) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalStateException("product with id " + id + " doesn't exist"));
        if (Objects.nonNull(name)) {
            product.setName(name);
        }
        if (Objects.nonNull(description)) {
            product.setDescription(description);
        }
        if (Objects.nonNull(price)) {
            product.setPrice(price);
        }
        return product;
    }
}
