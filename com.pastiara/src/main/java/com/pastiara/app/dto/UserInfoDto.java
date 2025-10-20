package com.pastiara.app.dto;

import java.util.Objects;

public class UserInfoDto {
	private Long identificador;
	private String name;
	private String lastName;
	private String telephone;
	private String state;
	private String zipCode;
	private String street;
	
	public UserInfoDto() {}

	public UserInfoDto(Long identificador, String name, String lastName, String telephone, String state, String zipCode,
			String street) {
		super();
		this.identificador = identificador;
		this.name = name;
		this.lastName = lastName;
		this.telephone = telephone;
		this.state = state;
		this.zipCode = zipCode;
		this.street = street;
	}

	public Long getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
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
		builder.append("UserInfoDto [identificador=");
		builder.append(identificador);
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
	// como representa datos de identidad y seguridad se agrega hashcode y equals
	
	@Override
	public int hashCode() {
		return Objects.hash(identificador, lastName, name, state, street, telephone, zipCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInfoDto other = (UserInfoDto) obj;
		return Objects.equals(identificador, other.identificador) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(name, other.name) && Objects.equals(state, other.state)
				&& Objects.equals(street, other.street) && Objects.equals(telephone, other.telephone)
				&& Objects.equals(zipCode, other.zipCode);
	}
	
	
}
