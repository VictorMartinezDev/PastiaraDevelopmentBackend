 package com.pastiara.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pastiara.app.model.UserInfo;
import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long>{

//===========Métodos personalizados==============================
// Obtener información de usuario por su id de usuario

    Optional<UserInfo> findByUserId(Long userId);

    // Buscar por código psotal

    Optional<UserInfo> findByZipCode(String zipCode);

}