package com.spring.demo.core.web;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("products")
public class ProductsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsController.class);

    public ProductsController() {
        LOGGER.info("Initializing the rest controller for the products");
    }

    @PostMapping("/products-body")
    public ResponseEntity<Object> getProductsQuery(@RequestBody ProductResourceRecord product) {
        return ResponseEntity.of(Optional.ofNullable(product));
    }

    @GetMapping("/products-header")
    public ResponseEntity<Object> getProductsHeader(@RequestHeader("name") String name) {
        return ResponseEntity.of(Optional.ofNullable(name));
    }

    @GetMapping("/products-query")
    public ResponseEntity<Object> getProductsQuery(@RequestParam("name") String name) {
        return ResponseEntity.of(Optional.ofNullable(name));
    }

    @GetMapping("/products-path/{id}")
    public ResponseEntity<Object> getProductsPath(@PathVariable("id") String id) {
        return ResponseEntity.of(Optional.ofNullable(id));
    }

    @GetMapping("/products-discount")
    public ResponseEntity<Object> getProductsDiscount(@RequestAttribute("discount") Double discount) {
        return ResponseEntity.of(Optional.ofNullable(discount));
    }
}
