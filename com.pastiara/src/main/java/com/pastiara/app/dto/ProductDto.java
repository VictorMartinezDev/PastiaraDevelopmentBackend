package com.pastiara.app.dto;

public class ProductDto {
	private Integer identificador;
	private String productName;
	private String category;
	private Integer productPrice;
	private String productDescription;
	
	public ProductDto() {}

	public ProductDto(Integer identificador, String productName, String category, Integer productPrice,
			String productDescription) {
		super();
		this.identificador = identificador;
		this.productName = productName;
		this.category = category;
		this.productPrice = productPrice;
		this.productDescription = productDescription;
	}

	public Integer getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductDto [identificador=");
		builder.append(identificador);
		builder.append(", productName=");
		builder.append(productName);
		builder.append(", category=");
		builder.append(category);
		builder.append(", productPrice=");
		builder.append(productPrice);
		builder.append(", productDescription=");
		builder.append(productDescription);
		builder.append("]");
		return builder.toString();
	}
	

	
}
