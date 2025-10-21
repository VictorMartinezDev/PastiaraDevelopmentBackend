package com.pastiara.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pastiara.app.dto.FavoriteDto;
import com.pastiara.app.service.FavoriteService;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "*")
public class FavoriteController {
    
    private final FavoriteService favoriteService;
    
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }
    
    // GET - Obtener todos los favoritos
    @GetMapping
    public ResponseEntity<List<FavoriteDto>> getAllFavorites() {
        List<FavoriteDto> favorites = favoriteService.findAll();
        return ResponseEntity.ok(favorites);
    }
    
    // GET - Obtener favoritos de un usuario específico (ver mis favoritos) 
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavoriteDto>> getFavoritesByUserId(@PathVariable Long userId) {
        List<FavoriteDto> favorites = favoriteService.findByUserId(userId);
        return ResponseEntity.ok(favorites);
    }
    
    // GET - Obtener favorito por ID
    @GetMapping("/{id}")
    public ResponseEntity<FavoriteDto> getFavoriteById(@PathVariable Integer id) {
        try {
            FavoriteDto favorite = favoriteService.findById(id);
            return ResponseEntity.ok(favorite);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build(); 
        }
    }
    
    // POST - Agregar producto a favoritos (marcar como favorito ❤️)
    @PostMapping
    public ResponseEntity<FavoriteDto> addFavorite(@RequestBody FavoriteDto favoriteDto) {
        FavoriteDto savedFavorite = favoriteService.save(favoriteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFavorite);
    }
    
    // DELETE - Quitar de favoritos (desmarcar favorito)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Integer id) {
        try {
            favoriteService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
}