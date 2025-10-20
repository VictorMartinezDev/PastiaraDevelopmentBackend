package com.pastiara.app.dto;

import java.util.Objects;

//para mayor seguridad se harán dos dto para el caso de User.java para mayor 
//seguridad de los datos. 

/*
 * 1. Dto de ENTRADA (Request): UserRegistrationDto
DTO que reciba la contraseña, ya que es esencial para crear o autenticar al usuario.

Campos clave: email y password.

Uso: Controller para /register o /login
 */


public class UserRegistrationDto {
	private String email;
	private String password;
	
	public UserRegistrationDto() {}

	public UserRegistrationDto(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserRegistrationDto [email=");
		builder.append(email);
		builder.append(", password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}
//este al igual que userinfo requiere que se incluya hashcode y equals
	@Override
	public int hashCode() {
		return Objects.hash(email, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRegistrationDto other = (UserRegistrationDto) obj;
		return Objects.equals(email, other.email) && Objects.equals(password, other.password);
	}
	
	

	
}
