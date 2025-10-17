package com.pastiara.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Favorites")
public class Favorite {
	
	@Id
	private Integer favoritesId;
	
	@ManyToOne
	@JoinColumn(name = "UserId")
	User user;
	
	@ManyToOne
	@JoinColumn(name = "productId")
	Product product;
	
	
}