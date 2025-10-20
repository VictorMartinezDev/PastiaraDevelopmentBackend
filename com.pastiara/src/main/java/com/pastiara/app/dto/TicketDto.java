package com.pastiara.app.dto;

import java.time.LocalDate;
import java.util.List;

//Se tiene que importar el Dto de Producto para incluir los detalles de los items

public class TicketDto {
	
	private Long identificador; // Este es para mapear el ticketId
	private LocalDate ticketDate;
	private Integer total;
	private String eventType;
	
	// Se remplaza el objeto User por su ID
	private Long userId; 
	
	// Se remplaza la lista de Entidades (Product) por una lista de DTOs
	private List<ProductDto> products; 
	
	public TicketDto() {}

	public TicketDto(Long identificador, LocalDate ticketDate, Integer total, String eventType, Long userId, List<ProductDto> products) {
		// QUITAMOS el super()
		this.identificador = identificador;
		this.ticketDate = ticketDate;
		this.total = total;
		this.eventType = eventType;
		this.userId = userId;
		this.products = products;
	}

	public Long getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}

	public LocalDate getTicketDate() {
		return ticketDate;
	}

	public void setTicketDate(LocalDate ticketDate) {
		this.ticketDate = ticketDate;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<ProductDto> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDto> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TicketDto [identificador=");
		builder.append(identificador);
		builder.append(", ticketDate=");
		builder.append(ticketDate);
		builder.append(", total=");
		builder.append(total);
		builder.append(", eventType=");
		builder.append(eventType);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", products=");
		builder.append(products);
		builder.append("]");
		return builder.toString();
	}
	
}

