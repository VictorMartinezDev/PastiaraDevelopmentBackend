package com.pastiara.app.service.impl;

import org.springframework.stereotype.Service;
import com.pastiara.app.dto.ProductDto;
import com.pastiara.app.model.Product;
import com.pastiara.app.repository.ProductRepository;
import com.pastiara.app.service.ProductService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de productos.
 * Es donde se realiza toda la lógica de negocio para gestionar productos.
 */
@Service
public class ProductServiceImpl implements ProductService {
	
	// Inyección del repositorio para acceder a la base de datos
	private final ProductRepository productRepository;
	
	//constructor 
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	/**
	 * Guarda un nuevo producto en la base de datos.
	 * 
	 * @param productDto - DTO con los datos del producto a crear
	 * @return ProductDto - DTO del producto guardado (con ID generado)
	 */
	@Override
	public ProductDto save(ProductDto productDto) {
		
		// Paso 1: Convertir DTO a Entity (para guardar en BD)
		Product product = convertToEntity(productDto);
		
		// Paso 2: Guardar en la base de datos usando el repository
		Product savedProduct = productRepository.save(product);
		
		// Paso 3: Convertir Entity a DTO (para devolver al controller)
		return convertToDto(savedProduct);
	}
	
	/**
	 * Busca un producto por su ID.
	 * 
	 * @param id - ID del producto a buscar
	 * @return ProductDto - DTO del producto encontrado
	 * @throws IllegalStateException si el producto no existe
	 */
	@Override
	public ProductDto findById(Integer id) {
		// Paso 1: Buscar en la base de datos (devuelve Optional)
		Optional<Product> optionalProduct = productRepository.findById(id);
		
		// Paso 2: Verificar si existe
		if (optionalProduct.isEmpty()) {
			// Si no existe, lanzar excepción (el controller la captura)
			throw new IllegalStateException("Product does not exist with id " + id);
		}
		
		// Paso 3: Si existe, convertir a DTO y devolver
		return convertToDto(optionalProduct.get());
	}
	
	/**
	 * Obtiene todos los productos del catálogo.
	 * 
	 * @return List<ProductDto> - Lista de todos los productos
	 */
	@Override
	public List<ProductDto> findAll() {
		// Paso 1: Obtener todas las entities de la BD
		// Paso 2: Convertir cada entity a DTO usando stream
		// Paso 3: Recolectar en una lista
		return productRepository.findAll()
				.stream()                          // Convertir lista a stream
				.map(this::convertToDto)           // Aplicar conversión a cada elemento
				.collect(Collectors.toList());     // Recolectar en lista
	}
	
	/**
	 * Actualiza un producto existente.
	 * 
	 * @param id - ID del producto a actualizar
	 * @param productDto - DTO con los nuevos datos
	 * @return ProductDto - DTO del producto actualizado
	 * @throws IllegalStateException si el producto no existe
	 */
	@Override
	public ProductDto update(Integer id, ProductDto productDto) {
		// Paso 1: Verificar que el producto existe
		Optional<Product> optionalProduct = productRepository.findById(id);
		if (optionalProduct.isEmpty()) {
			throw new IllegalStateException("Product does not exist with id " + id);
		}
		
		// Paso 2: Obtener el producto existente
		Product existingProduct = optionalProduct.get();
		
		// Paso 3: Actualizar solo los campos que pueden cambiar
		existingProduct.setProductName(productDto.getProductName());
		existingProduct.setCategory(productDto.getCategory());
		existingProduct.setProductPrice(productDto.getProductPrice());
		existingProduct.setProductDescription(productDto.getProductDescription());
		// NO actualizamos el ID porque ya existe
		
		// Paso 4: Guardar los cambios en la BD
		Product updatedProduct = productRepository.save(existingProduct);
		
		// Paso 5: Convertir a DTO y devolver
		return convertToDto(updatedProduct);
	}
	
	/**
	 * Elimina un producto por su ID.
	 * 
	 * @param id - ID del producto a eliminar
	 * @throws IllegalStateException si el producto no existe
	 */
	@Override
	public void deleteById(Integer id) {
		// Paso 1: Verificar que el producto existe antes de eliminar
		Optional<Product> optionalProduct = productRepository.findById(id);
		if (optionalProduct.isEmpty()) {
			throw new IllegalStateException("Product does not exist with id " + id);
		}
		
		// Paso 2: Eliminar de la base de datos
		productRepository.deleteById(id);
	}
	
	// ========== Métodos privados de conversión ==========
	
	/**
	 * Convierte una Entity (Product) a DTO (ProductDto).
	 * Esto se hace para no exponer la entidad directamente al controller.
	 * 
	 * @param product - Entity de la base de datos
	 * @return ProductDto - DTO para enviar al controller
	 */
	private ProductDto convertToDto(Product product) {
		// Crear un nuevo DTO con los datos de la entity
		return new ProductDto(
			product.getProductId(),           // identificador
			product.getProductName(),
			product.getCategory(),
			product.getProductPrice(),
			product.getProductDescription()
		);
	}
	
	/**
	 * Convierte un DTO (ProductDto) a Entity (Product).
	 * Esto se hace para guardar en la base de datos.
	 * 
	 * @param dto - DTO recibido del controller
	 * @return Product - Entity para guardar en BD
	 */
	private Product convertToEntity(ProductDto dto) {
		Product product = new Product();
		
		// Solo asignar ID si existe (para updates)
		// En creaciones nuevas, el ID es null y la BD lo genera
		if (dto.getIdentificador() != null) {
			product.setProductId(dto.getIdentificador());
		}
		
		// Asignar el resto de campos
		product.setProductName(dto.getProductName());
		product.setCategory(dto.getCategory());
		product.setProductPrice(dto.getProductPrice());
		product.setProductDescription(dto.getProductDescription());
		
		return product;
	}
}
