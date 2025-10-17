
package com.pastiara.app.service;

import org.springframework.stereotype.Service;
import com.pastiara.app.model.Product;
import com.pastiara.app.repository.ProductRepository;
import java.util.List;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	// Crear o actualizar producto
	public Product save(Product product) {
		return productRepository.save(product);
	}

	// Obtener todos los productos
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	// Buscar un producto por ID
	public Product findById(Integer id) {
		return productRepository.findById(id).orElse(null);
	}
	
	// Eliminar producto
	public void delete(Integer id) {
		productRepository.deleteById(id);
	}
}
