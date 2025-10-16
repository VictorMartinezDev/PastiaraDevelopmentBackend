package com.pastiara.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pastiara.app.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
