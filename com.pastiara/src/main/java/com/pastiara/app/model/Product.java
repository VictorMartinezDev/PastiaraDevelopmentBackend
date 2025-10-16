package com.pastiara.app.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
	
	@Id
	private Integer productId;
	@Column(length = 30, nullable= false)
	private String productName;
	@Column(length = 20, nullable= false)
	private String category;
	@Column(nullable= false)
	private Integer productPrice;
	@Column(length = 50, nullable= false)
	private String productDescription;
	
	@OneToMany(mappedBy="product")
	private List<Favorite> favorite;
	
	@ManyToMany(mappedBy = "product")
	List<Ticket> tickets;
}
