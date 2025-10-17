package com.pastiara.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pastiara.app.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	//===========Métodos personalizados==============================
	
	// Buscar productos por nombre
    List<Product> findByNameContainingIgnoreCase(String keyword);

    // Buscar productos por categoría
    List<Product> findByCategory(String category);

    // Verificar si un producto existe por nombre
    boolean existsByName(String name);

}
