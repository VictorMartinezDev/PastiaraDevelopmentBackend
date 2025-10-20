package com.pastiara.app.dto;

import java.util.Objects;

// este es el otro Dto de User.java

/*
 * 2. Dto de SALIDA (Response): UserResponseDto
 *  DTO para enviar información del usuario al cliente. 

====NUNCA se debe de incluir la contraseña (ni siquiera hasheada) en una respuesta de la API.===

Campos clave: identificador (userId) y email.

Uso: Controller para responder después de un login o al obtener datos del perfil.
 */
public class UserResponseDto {
	
	//aquí no se incluye password por seguridad 
	private Long identificador;
	private String email;
	
	public UserResponseDto() {}

	public UserResponseDto(Long identificador, String email) {
		super();
		this.identificador = identificador;
		this.email = email;
	}

	public Long getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Long identificador) {
		this.identificador = identificador;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserResponseDto [identificador=");
		builder.append(identificador);
		builder.append(", email=");
		builder.append(email);
		builder.append("]");
		return builder.toString();
	}
	
	//este al igual que userinfo requiere que se incluya hashcode y equals

	@Override
	public int hashCode() {
		return Objects.hash(email, identificador);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserResponseDto other = (UserResponseDto) obj;
		return Objects.equals(email, other.email) && Objects.equals(identificador, other.identificador);
	}
	
	
	
	
}
