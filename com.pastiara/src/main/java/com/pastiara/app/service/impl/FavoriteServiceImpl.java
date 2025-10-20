package com.pastiara.app.service.impl;

import org.springframework.stereotype.Service;
import com.pastiara.app.dto.FavoriteDto;
import com.pastiara.app.model.Favorite;
import com.pastiara.app.model.User;
import com.pastiara.app.model.Product;
import com.pastiara.app.repository.FavoriteRepository;
import com.pastiara.app.repository.UserRepository;
import com.pastiara.app.repository.ProductRepository;
import com.pastiara.app.service.FavoriteService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de favoritos.
 * * Este servicio se encarga de gestionar la lógica de negocio para
 * añadir, buscar y eliminar productos marcados como favoritos por los usuarios.
 * Utiliza DTOs para el intercambio de datos y Repositorios para la persistencia.
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {
	
	// Inyección de repositorios
	private final FavoriteRepository favoriteRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	
	/**
	 * Constructor con inyección de dependencias.
	 * * @param favoriteRepository Repositorio para la entidad Favorite.
	 * @param userRepository Repositorio para la entidad User (necesario para las relaciones).
	 * @param productRepository Repositorio para la entidad Product (necesario para las relaciones).
	 */
	public FavoriteServiceImpl(FavoriteRepository favoriteRepository,
	                           UserRepository userRepository,
	                           ProductRepository productRepository) {
		this.favoriteRepository = favoriteRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
	}
	
	/**
	 * Guarda un nuevo registro de favorito en la base de datos.
	 * * @param favoriteDto DTO con el ID de usuario y ID de producto.
	 * @return FavoriteDto DTO del favorito guardado, incluyendo su nuevo ID.
	 */
	@Override
	public FavoriteDto save(FavoriteDto favoriteDto) {
		// TODO: Validar que no exista ya este favorito (mismo user + mismo product)
		
		// Paso 1: Convertir el DTO de entrada (que solo tiene IDs) a la Entidad Favorite.
		//          Esto implica buscar las entidades User y Product por sus IDs.
		Favorite favorite = convertToEntity(favoriteDto);
		
		// Paso 2: Guardar la entidad Favorite completa en la base de datos.
		Favorite savedFavorite = favoriteRepository.save(favorite);
		
		// Paso 3: Convertir la entidad guardada de vuelta a DTO (con el ID generado) y devolver.
		return convertToDto(savedFavorite);
	}
	
	/**
	 * Busca un registro de favorito por su ID principal.
	 * * @param id ID del registro Favorite.
	 * @return FavoriteDto DTO del favorito encontrado.
	 */
	@Override
	public FavoriteDto findById(Integer id) {
		// Paso 1: Buscar el favorito por ID en la base de datos.
		Optional<Favorite> optionalFavorite = favoriteRepository.findById(id);
		
		// Paso 2: Verificar si el registro existe. Si no, lanza una excepción.
		if (optionalFavorite.isEmpty()) {
			throw new IllegalStateException("Favorite does not exist with id " + id);
		}
		
		// Paso 3: Convertir la entidad encontrada a DTO y devolver.
		return convertToDto(optionalFavorite.get());
	}
	
	/**
	 * Obtiene todos los registros de favoritos del sistema.
	 * * @return List<FavoriteDto> Lista de todos los favoritos.
	 */
	@Override
	public List<FavoriteDto> findAll() {
		// Paso 1: Obtener todos los registros de la base de datos.
		// Paso 2: Usar Stream para mapear cada entidad Favorite a FavoriteDto.
		// Paso 3: Recolectar el resultado en una lista y devolver.
		return favoriteRepository.findAll()
				.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}
	
	/**
	 * Elimina un registro de favorito por su ID.
	 * * @param id ID del registro Favorite a eliminar.
	 */
	@Override
	public void deleteById(Integer id) {
		// Paso 1: Verificar que el registro de favorito exista antes de intentar eliminar.
		Optional<Favorite> optionalFavorite = favoriteRepository.findById(id);
		if (optionalFavorite.isEmpty()) {
			throw new IllegalStateException("Favorite does not exist with id " + id);
		}
		
		// Paso 2: Eliminar el registro de la base de datos.
		favoriteRepository.deleteById(id);
	}
	
	/**
	 * Obtiene todos los favoritos asociados a un ID de usuario específico.
	 * * @param userId ID del usuario.
	 * @return List<FavoriteDto> Lista de favoritos del usuario.
	 */
	@Override
	public List<FavoriteDto> findByUserId(Long userId) {
		// Paso 1: Buscar en el repositorio todos los favoritos usando el ID del usuario.
		//          (Usando el método findByUserUserId definido en el Repository).
		// Paso 2: Usar Stream para mapear las entidades Favorite a FavoriteDto.
		// Paso 3: Recolectar el resultado en una lista y devolver.
		return favoriteRepository.findByUserUserId(userId)
				.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}
	
	// ========== Métodos privados de conversión (Mapper) ==========
	
	/**
	 * Convierte la Entidad (Favorite) a DTO (FavoriteDto).
	 * * Este método extrae los IDs de las entidades relacionadas (User y Product)
	 * y los coloca en el DTO, desacoplando la capa de servicio de las entidades JPA.
	 * * @param favorite Entidad de la base de datos.
	 * @return FavoriteDto DTO para enviar al Controller.
	 */
	private FavoriteDto convertToDto(Favorite favorite) {
		// Se usa un ternario para verificar si las relaciones son nulas antes de acceder a los IDs.
		return new FavoriteDto(
			favorite.getFavoritesId(),
			favorite.getUser() != null ? favorite.getUser().getUserId() : null,
			favorite.getProduct() != null ? favorite.getProduct().getProductId() : null
		);
	}
	
	/**
	 * Convierte el DTO (FavoriteDto) a Entidad (Favorite).
	 * * Este método es fundamental para las operaciones de guardado, ya que
	 * debe buscar las Entidades User y Product en la BD para establecer
	 * correctamente las relaciones ManyToOne en la Entidad Favorite.
	 * * @param dto DTO recibido del Controller.
	 * @return Favorite Entidad para guardar en BD.
	 */
	private Favorite convertToEntity(FavoriteDto dto) {
		Favorite favorite = new Favorite();
		
		// Paso 1: Si el DTO tiene un ID, se establece (útil para actualizaciones).
		if (dto.getIdentificador() != null) {
			favorite.setFavoritesId(dto.getIdentificador());
		}
		
		// Paso 2: Asignar el User (relación ManyToOne)
		if (dto.getUserId() != null) {
			// Buscar la Entidad User por el ID del DTO. Si no existe, lanza excepción.
			User user = userRepository.findById(dto.getUserId())
					.orElseThrow(() -> new IllegalStateException("User not found with id " + dto.getUserId()));
			favorite.setUser(user);
		}
		
		// Paso 3: Asignar el Product (relación ManyToOne)
		if (dto.getProductId() != null) {
			// Buscar la Entidad Product por el ID del DTO. Si no existe, lanza excepción.
			Product product = productRepository.findById(dto.getProductId())
					.orElseThrow(() -> new IllegalStateException("Product not found with id " + dto.getProductId()));
			favorite.setProduct(product);
		}
		
		return favorite;
	}
}