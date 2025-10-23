package com.pastiara.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pastiara.app.model.User;
// import java.util.List;
// import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	public boolean existsByEmailAndPassword(String email, String password);
	
	Optional<User> findByEmail(String email);

}
