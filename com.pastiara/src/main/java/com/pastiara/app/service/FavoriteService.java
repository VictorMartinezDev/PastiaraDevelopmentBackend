package com.pastiara.app.service;

import com.pastiara.app.dto.FavoriteDto;
import java.util.List;

public interface FavoriteService {
	
	FavoriteDto save(FavoriteDto favoriteDto);
	
	FavoriteDto findById(Integer id);
	
	List<FavoriteDto> findAll();
	
	void deleteById(Integer id);
	
	// Método adicional: Obtener favoritos de un usuario
	List<FavoriteDto> findByUserId(Long userId);
	
}
