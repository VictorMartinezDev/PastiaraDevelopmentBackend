package com.pastiara.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	private List<Favorite> favorite = new ArrayList<>();
	
	@ManyToMany(mappedBy = "product")
	List<Ticket> tickets = new ArrayList<>();
	
	public Product () {}

	public Product(Integer productId, String productName, String category, Integer productPrice,
			String productDescription, List<Favorite> favorite) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.category = category;
		this.productPrice = productPrice;
		this.productDescription = productDescription;
		this.favorite = favorite;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public List<Favorite> getFavorite() {
		return favorite;
	}

	public void setFavorite(List<Favorite> favorite) {
		this.favorite = favorite;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product [productId=");
		builder.append(productId);
		builder.append(", productName=");
		builder.append(productName);
		builder.append(", category=");
		builder.append(category);
		builder.append(", productPrice=");
		builder.append(productPrice);
		builder.append(", productDescription=");
		builder.append(productDescription);
		builder.append(", favorite=");
		builder.append(favorite);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, favorite, productDescription, productId, productName, productPrice);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(category, other.category) && Objects.equals(favorite, other.favorite)
				&& Objects.equals(productDescription, other.productDescription)
				&& Objects.equals(productId, other.productId) && Objects.equals(productName, other.productName)
				&& Objects.equals(productPrice, other.productPrice);
	}
	
	
}
