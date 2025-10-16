package com.pastiara.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pastiara.app.model.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer>{

}
