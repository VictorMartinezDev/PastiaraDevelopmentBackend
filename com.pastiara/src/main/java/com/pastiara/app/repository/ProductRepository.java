package com.pastiara.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pastiara.app.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
