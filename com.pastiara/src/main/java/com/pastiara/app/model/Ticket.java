package com.pastiara.app.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Tickets")
public class Ticket {
	
	@Id
	private Long ticketId;
	@Column(nullable = false)
	private LocalDate ticketDate;
	@Column(nullable = false)
	private Integer total;
	@Column(length = 20, nullable = false)
	private String eventType;
	
	@ManyToOne
	@JoinColumn(name = "UserId")
	private User user;
	
	@ManyToMany
	@JoinTable(
				name = "products_has_tickets",
				joinColumns = @JoinColumn(name = "tikectID"),
				inverseJoinColumns = @JoinColumn(name = "productID")
			)
	List<Product> product = new ArrayList<>();
	
	public Ticket() {}

	public Ticket(Long ticketId, LocalDate ticketDate, Integer total, String eventType, User user) {
		this.ticketId = ticketId;
		this.ticketDate = ticketDate;
		this.total = total;
		this.eventType = eventType;
		this.user = user;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Product> getProduct() {
		return product;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ticket [ticketId=");
		builder.append(ticketId);
		builder.append(", ticketDate=");
		builder.append(ticketDate);
		builder.append(", total=");
		builder.append(total);
		builder.append(", eventType=");
		builder.append(eventType);
		builder.append(", user=");
		builder.append(user);
		builder.append(", product=");
		builder.append(product);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(eventType, ticketDate, ticketId, total, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		return Objects.equals(eventType, other.eventType) && Objects.equals(ticketDate, other.ticketDate)
				&& Objects.equals(ticketId, other.ticketId) && Objects.equals(total, other.total)
				&& Objects.equals(user, other.user);
	};
	
	
}
