package com.pastiara.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pastiara.app.dto.ProductDto;
import com.pastiara.app.service.ProductService;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    
    private final ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    // GET - Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.findAll();
        return ResponseEntity.ok(products);
    }
    
    // GET - Obtener producto por ID 
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Integer id) {
        try {
            ProductDto product = productService.findById(id);
            return ResponseEntity.ok(product);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // POST - Crear nuevo producto (admin)
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto savedProduct = productService.save(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }
    
    // PUT - Actualizar producto existente (admin)
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct( 
            @PathVariable Integer id, 
            @RequestBody ProductDto productDto) {
        try {
            ProductDto updatedProduct = productService.update(id, productDto);
            return ResponseEntity.ok(updatedProduct);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // DELETE - Eliminar producto (admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        try {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
}