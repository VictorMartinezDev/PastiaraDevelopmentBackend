package com.pastiara.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pastiara.app.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
	
	//===========Métodos personalizados==============================

	Optional<User> findByEmail(String email);
	
	List<User> findAll();
}
