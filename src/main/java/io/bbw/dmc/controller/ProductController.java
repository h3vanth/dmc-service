package io.bbw.dmc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import lombok.RequiredArgsConstructor;

import io.bbw.dmc.pojo.Product;
import io.bbw.dmc.service.ProductService;
import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/v1/products")
    public ResponseEntity<List<Product>> getProducts(Principal principal) {
        return new ResponseEntity<>(productService.getProducts(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/v1/products/{productId}")
    public ResponseEntity<Product> getProductByProductId(@PathVariable String productId) {
        return new ResponseEntity<>(productService.getProductByProductId(productId),
                HttpStatus.OK);
    }

    @PutMapping("/v1/products")
    public ResponseEntity<HttpStatus> updateProduct(@Valid @RequestBody Product product) {
        productService.updateProduct(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/v1/products")
    public ResponseEntity<?> addProduct(@RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("productName") String productName,
            @RequestParam("price") BigDecimal price,
            @RequestParam("availableQuantity") int availableQuantity,
            @RequestParam("description") String description,
            @RequestParam("isAvailable") boolean isAvailable,
            Principal principal) {
        Product product = Product.builder().productName(productName).price(price).availableQuantity(availableQuantity)
                .description(description).isAvailable(isAvailable).build();
        return new ResponseEntity<>(productService.addProduct(product, principal.getName()), HttpStatus.CREATED);
    }

    @DeleteMapping("/v1/products/{productIds}")
    public ResponseEntity<Product> deleteProducts(@PathVariable String productIds) {
        productService.deleteProducts(productIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
