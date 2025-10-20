package com.pastiara.app.service.impl;

import org.springframework.stereotype.Service;
import com.pastiara.app.dto.UserRegistrationDto;
import com.pastiara.app.dto.UserResponseDto;
import com.pastiara.app.dto.UserInfoDto;
import com.pastiara.app.model.User;
import com.pastiara.app.model.UserInfo;
import com.pastiara.app.repository.UserRepository;
import com.pastiara.app.repository.UserInfoRepository;
import com.pastiara.app.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de usuarios.
 * 
 * SEGURIDAD: Este servicio maneja dos tipos de DTOs:
 * - UserRegistrationDto: Para RECIBIR datos (incluye password)
 * - UserResponseDto: Para ENVIAR datos (SIN password - seguridad)
 */
@Service
public class UserServiceImpl implements UserService {
	
	// Inyección de repositorios
	private final UserRepository userRepository;
	private final UserInfoRepository userInfoRepository;
	// private final PasswordEncoder passwordEncoder; 
	// TODO: Agregar cuando implementes Spring Security
	
	/**
	 * Constructor con inyección de dependencias.
	 */
	public UserServiceImpl(UserRepository userRepository, UserInfoRepository userInfoRepository) {
		this.userRepository = userRepository;
		this.userInfoRepository = userInfoRepository;
	}
	
	/**
	 * Registra un nuevo usuario en el sistema.
	 * 
	 * @param userRegistrationDto - DTO con email y password
	 * @return UserResponseDto - DTO del usuario creado (SIN password)
	 */
	@Override
	public UserResponseDto save(UserRegistrationDto userRegistrationDto) {
		// TODO: Aquí deberías agregar validaciones:
		// - Verificar que el email no exista ya (usuario duplicado)
		// - Validar formato de email
		// - Validar que el password cumpla requisitos (mínimo 8 caracteres, etc.)
		
		// TODO: IMPORTANTE - Encriptar la contraseña antes de guardar
		// NUNCA guardes passwords en texto plano en la base de datos
		// user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
		
		// Paso 1: Convertir DTO de registro a Entity
		User user = convertRegistrationToEntity(userRegistrationDto);
		
		// Imprimir en consola (útil para debugging)
		System.out.println(user);
		
		// Paso 2: Guardar en la base de datos
		User savedUser = userRepository.save(user);
		
		// Paso 3: Convertir a DTO de respuesta (SIN password) y devolver
		return convertToResponseDto(savedUser);
	}
	
	/**
	 * Busca un usuario por su ID.
	 * 
	 * @param id - ID del usuario
	 * @return UserResponseDto - DTO del usuario (SIN password)
	 */
	@Override
	public UserResponseDto findById(Long id) {
		// Buscar en la base de datos
		Optional<User> optionalUser = userRepository.findById(id);
		
		// Verificar si existe
		if (optionalUser.isEmpty()) {
			throw new IllegalStateException("User does not exist with id " + id);
		}
		
		// Convertir a DTO de respuesta (sin password) y devolver
		return convertToResponseDto(optionalUser.get());
	}
	
	/**
	 * Obtiene todos los usuarios del sistema.
	 * 
	 * @return List<UserResponseDto> - Lista de usuarios (SIN passwords)
	 */
	@Override
	public List<UserResponseDto> findAll() {
		// Obtener todos los usuarios y convertir a DTOs de respuesta
		return userRepository.findAll()
				.stream()
				.map(this::convertToResponseDto)  // Convierte cada User a UserResponseDto
				.collect(Collectors.toList());
	}
	
	/**
	 * Actualiza los datos de un usuario (email y/o password).
	 * 
	 * @param id - ID del usuario a actualizar
	 * @param userRegistrationDto - DTO con los nuevos datos
	 * @return UserResponseDto - DTO del usuario actualizado (SIN password)
	 */
	@Override
	public UserResponseDto update(Long id, UserRegistrationDto userRegistrationDto) {
		// Paso 1: Verificar que el usuario existe
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			throw new IllegalStateException("User does not exist with id " + id);
		}
		User existingUser = optionalUser.get();
		
		// Paso 2: Actualizar el email
		existingUser.setEmail(userRegistrationDto.getEmail());
		
		// Paso 3: Solo actualizar password si viene uno nuevo
		// (Si el campo está vacío, mantener el password actual)
		if (userRegistrationDto.getPassword() != null && !userRegistrationDto.getPassword().isEmpty()) {
			// TODO: Encriptar la nueva contraseña
			// existingUser.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
			existingUser.setPassword(userRegistrationDto.getPassword());
		}
		
		// Paso 4: Guardar los cambios
		User updatedUser = userRepository.save(existingUser);
		
		// Paso 5: Convertir a DTO de respuesta y devolver
		return convertToResponseDto(updatedUser);
	}
	
	/**
	 * Elimina un usuario del sistema.
	 * 
	 * @param id - ID del usuario a eliminar
	 */
	@Override
	public void deleteById(Long id) {
		// Verificar que el usuario existe antes de eliminar
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			throw new IllegalStateException("User does not exist with id " + id);
		}
		
		// Eliminar de la base de datos
		userRepository.deleteById(id);
	}
	
	// ========== Métodos para UserInfo (perfil del usuario) ==========
	
	/**
	 * Obtiene la información del perfil de un usuario.
	 * 
	 * UserInfo contiene: nombre, apellido, teléfono, dirección, etc.
	 * 
	 * @param userId - ID del usuario
	 * @return UserInfoDto - DTO con la información del perfil
	 */
	@Override
	public UserInfoDto getUserInfo(Long userId) {
		// Buscar la información del perfil
		Optional<UserInfo> optionalUserInfo = userInfoRepository.findByUserId(userId);
		
		// Verificar si existe
		if (optionalUserInfo.isEmpty()) {
			throw new IllegalStateException("UserInfo does not exist for user id " + userId);
		}
		
		// Convertir a DTO y devolver
		return convertUserInfoToDto(optionalUserInfo.get());
	}
	
	/**
	 * Actualiza o crea la información del perfil de un usuario.
	 * 
	 * @param userId - ID del usuario
	 * @param userInfoDto - DTO con los datos del perfil
	 * @return UserInfoDto - DTO del perfil actualizado
	 */
	@Override
	public UserInfoDto updateUserInfo(Long userId, UserInfoDto userInfoDto) {
		// Buscar si ya existe información de perfil para este usuario
		Optional<UserInfo> optionalUserInfo = userInfoRepository.findByUserId(userId);
		
		UserInfo userInfo;
		if (optionalUserInfo.isPresent()) {
			// Caso 1: Ya existe - ACTUALIZAR
			userInfo = optionalUserInfo.get();
		} else {
			// Caso 2: No existe - CREAR nuevo
			userInfo = new UserInfo();
			userInfo.setUserId(userId);
			
			// Verificar que el usuario existe antes de crear su perfil
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new IllegalStateException("User not found with id " + userId));
			userInfo.setUser(user);
		}
		
		// Actualizar todos los campos del perfil
		userInfo.setName(userInfoDto.getName());
		userInfo.setLastName(userInfoDto.getLastName());
		userInfo.setTelephone(userInfoDto.getTelephone());
		userInfo.setState(userInfoDto.getState());
		userInfo.setZipCode(userInfoDto.getZipCode());
		userInfo.setStreet(userInfoDto.getStreet());
		
		// Guardar en la base de datos
		UserInfo savedUserInfo = userInfoRepository.save(userInfo);
		
		// Convertir a DTO y devolver
		return convertUserInfoToDto(savedUserInfo);
	}
	
	// ========== Métodos privados de conversión ==========
	
	/**
	 * Convierte una Entity (User) a DTO de respuesta (UserResponseDto).
	 * 
	 * SEGURIDAD: NO incluye el password en el DTO de respuesta.
	 * Nunca expongas passwords al frontend, aunque estén encriptados.
	 * 
	 * @param user - Entity de la base de datos
	 * @return UserResponseDto - DTO para enviar al controller (SIN password)
	 */
	private UserResponseDto convertToResponseDto(User user) {
		return new UserResponseDto(
			user.getUserId(),    // identificador
			user.getEmail()      // email
			// NO incluimos el password por seguridad ✅
		);
	}
	
	/**
	 * Convierte un DTO de registro (UserRegistrationDto) a Entity (User).
	 * 
	 * Este DTO SÍ contiene el password porque lo necesitamos para crear el usuario.
	 * 
	 * @param dto - DTO recibido del controller
	 * @return User - Entity para guardar en BD
	 */
	private User convertRegistrationToEntity(UserRegistrationDto dto) {
		User user = new User();
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());  // TODO: Encriptar antes de guardar
		return user;
	}
	
	/**
	 * Convierte una Entity (UserInfo) a DTO (UserInfoDto).
	 * 
	 * @param userInfo - Entity de la base de datos
	 * @return UserInfoDto - DTO para enviar al controller
	 */
	private UserInfoDto convertUserInfoToDto(UserInfo userInfo) {
		return new UserInfoDto(
			userInfo.getUserId(),      // identificador
			userInfo.getName(),
			userInfo.getLastName(),
			userInfo.getTelephone(),
			userInfo.getState(),
			userInfo.getZipCode(),
			userInfo.getStreet()
		);
	}
}