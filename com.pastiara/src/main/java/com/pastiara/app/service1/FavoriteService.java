package com.pastiara.app.service1;

import org.springframework.stereotype.Service;
import com.pastiara.app.model.Favorite;
import com.pastiara.app.repository.FavoriteRepository;
import java.util.List;

@Service
public class FavoriteService {
    
    private final FavoriteRepository favoriteRepository;
    
    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }
    
    // Crear o guardar favorito
    public Favorite save(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }
    
    // Obtener todos los favoritos
    public List<Favorite> findAll() {
        return favoriteRepository.findAll();
    }
    
    // Buscar favorito por ID
    public Favorite findById(Integer id) {
        return favoriteRepository.findById(id).orElse(null);
    }
    
    // Obtener favoritos de un usuario específico
    public List<Favorite> findByUserId(Long userId) {
        return favoriteRepository.findByUserUserId(userId);
    }
    
    // Eliminar favorito
    public void delete(Integer id) {
        favoriteRepository.deleteById(id);
    }
}