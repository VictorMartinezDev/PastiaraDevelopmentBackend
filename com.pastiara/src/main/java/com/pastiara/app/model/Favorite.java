package com.pastiara.app.model;

import java.util.Objects;

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
	
	public Favorite () {}

	public Favorite(Integer favoritesId, User user, Product product) {
		super();
		this.favoritesId = favoritesId;
		this.user = user;
		this.product = product;
	}

	public Integer getFavoritesId() {
		return favoritesId;
	}

	public void setFavoritesId(Integer favoritesId) {
		this.favoritesId = favoritesId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Favorite [favoritesId=");
		builder.append(favoritesId);
		builder.append(", user=");
		builder.append(user);
		builder.append(", product=");
		builder.append(product);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(favoritesId, product, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Favorite other = (Favorite) obj;
		return Objects.equals(favoritesId, other.favoritesId) && Objects.equals(product, other.product)
				&& Objects.equals(user, other.user);
	}
	
	
}