package com.pastiara.app.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Users_info")
public class UserInfo {
	@Id
	@Column(name = "UserID")
	private Long userId;
	private String name;
	private String lastName;
	private String telephone;
	private String state;
	private String zipCode;
	private String street;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "UserId")
	private User user;

	public UserInfo(Long userId, String name, String lastName, String telephone, String state, String zipCode,
			String street) {
		super();
		this.userId = userId;
		this.name = name;
		this.lastName = lastName;
		this.telephone = telephone;
		this.state = state;
		this.zipCode = zipCode;
		this.street = street;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserInfo [userId=");
		builder.append(userId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", telephone=");
		builder.append(telephone);
		builder.append(", state=");
		builder.append(state);
		builder.append(", zipCode=");
		builder.append(zipCode);
		builder.append(", street=");
		builder.append(street);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(lastName, name, state, street, telephone, userId, zipCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInfo other = (UserInfo) obj;
		return Objects.equals(lastName, other.lastName) && Objects.equals(name, other.name)
				&& Objects.equals(state, other.state) && Objects.equals(street, other.street)
				&& Objects.equals(telephone, other.telephone) && Objects.equals(userId, other.userId)
				&& Objects.equals(zipCode, other.zipCode);
	}

	
}
