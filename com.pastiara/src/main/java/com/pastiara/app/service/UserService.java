package com.pastiara.app.service;

import com.pastiara.app.dto.UserRegistrationDto;
import com.pastiara.app.dto.UserResponseDto;
//import com.pastiara.app.model.User;
//import com.pastiara.app.dto.LoginDto;
import com.pastiara.app.dto.UserInfoDto;
import java.util.List;

public interface UserService {

	// Registro y autenticación
	UserResponseDto save(UserRegistrationDto userRegistrationDto);

	// Consultas
	UserResponseDto findById(Long id);

	List<UserResponseDto> findAll();

	// Actualización (usando UserRegistrationDto para cambiar email/password)
	UserResponseDto update(Long id, UserRegistrationDto userRegistrationDto);

	void deleteById(Long id);

	// Métodos adicionales para UserInfo
	UserInfoDto getUserInfo(Long userId);

	UserInfoDto updateUserInfo(Long userId, UserInfoDto userInfoDto);

	// public UserRegistrationDto validarCredenciales(LoginDto loginDto) {
	// boolean validador =
	// userRepository.existsByEmailAndPassword(loginDto.getEmail(),
	// loginDto.getPassword());

	// UserRegistrationDto userRegistrationDto = new UserRegistrationDto();

	// if (validador) {
	// User user = this.userRepository.findById(loginDto.getEmail()).get();

	// userRegistrationDto.setEmail(user.getEmail());
	// userRegistrationDto.setPassword("*****");
	// userRegistrationDto.setIdentificador(user.getUserId());
	// userRegistrationDto.setName(user.getUserInfo().getName());
	// userRegistrationDto.setLastName(user.getUserInfo().getLastName());

	// return userRegistrationDto;

	// } else {
	// return new UserRegistrationDto();
	// }

	// }
	// Implement validation logic here
}