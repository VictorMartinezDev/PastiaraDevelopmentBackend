package com.pastiara.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pastiara.app.model.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer>{
	
	//===========Métodos personalizados==============================

	List<Favorite> findByUserUserId(Long userId);

}
