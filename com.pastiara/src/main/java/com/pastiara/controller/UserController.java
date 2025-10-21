package com.pastiara.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pastiara.app.dto.UserRegistrationDto;
import com.pastiara.app.dto.UserResponseDto;
import com.pastiara.app.dto.UserInfoDto;
import com.pastiara.app.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    // ==================== CRUD para User =====================
    
    // GET - Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }
    
    // GET - Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        try {
            UserResponseDto user = userService.findById(id);
            return ResponseEntity.ok(user);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // POST - Crear nuevo usuario (registro)
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        UserResponseDto savedUser = userService.save(userRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
    
    // PUT - Actualizar usuario (cambiar email/password) 
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id, 
            @RequestBody UserRegistrationDto userRegistrationDto) {
        try {
            UserResponseDto updatedUser = userService.update(id, userRegistrationDto);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // DELETE - Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // ==================== Endpoints para UserInfo ====================
    
    // GET - Obtener información del perfil del usuario
    @GetMapping("/{id}/info")
    public ResponseEntity<UserInfoDto> getUserInfo(@PathVariable Long id) {
        try {
            UserInfoDto userInfo = userService.getUserInfo(id);
            return ResponseEntity.ok(userInfo);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // PUT - Actualizar información del perfil del usuario
    @PutMapping("/{id}/info")
    public ResponseEntity<UserInfoDto> updateUserInfo(
            @PathVariable Long id, 
            @RequestBody UserInfoDto userInfoDto) {
        try {
            UserInfoDto updatedInfo = userService.updateUserInfo(id, userInfoDto);
            return ResponseEntity.ok(updatedInfo);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
}