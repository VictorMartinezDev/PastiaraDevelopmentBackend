package com.pastiara.app.dto;

import java.util.Objects;

public class FavoriteDto {
	
	
	private Integer identificador; // este se refiere al Integer favoritesId
	private Long userId; 
	private Integer productId; 
	
	public FavoriteDto() {}

	public FavoriteDto(Integer identificador, Long userId, Integer productId) {
		super();
		this.identificador = identificador;
		this.userId = userId;
		this.productId = productId;
	}

	public Integer getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FavoriteDto [identificador=");
		builder.append(identificador);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", productId=");
		builder.append(productId);
		builder.append("]");
		return builder.toString();
	}

	//en este igual se incluye hashcode y equals
	@Override
	public int hashCode() {
		return Objects.hash(identificador, productId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FavoriteDto other = (FavoriteDto) obj;
		return Objects.equals(identificador, other.identificador) && Objects.equals(productId, other.productId)
				&& Objects.equals(userId, other.userId);
	}
	
	
}

	