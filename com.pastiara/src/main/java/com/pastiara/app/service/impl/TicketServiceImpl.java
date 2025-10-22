package com.pastiara.app.service.impl;

import org.springframework.stereotype.Service;
import com.pastiara.app.dto.ProductDto;
import com.pastiara.app.dto.TicketDto;
import com.pastiara.app.exception.ResourceNotFoundException;
import com.pastiara.app.model.Product;
import com.pastiara.app.model.Ticket;
import com.pastiara.app.model.User;
import com.pastiara.app.repository.ProductRepository;
import com.pastiara.app.repository.TicketRepository;
import com.pastiara.app.repository.UserRepository;
import com.pastiara.app.service.TicketService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de tickets (pedidos).
 */
@Service
public class TicketServiceImpl implements TicketService {
	
	// Inyección de los repositorios necesarios
	private final TicketRepository ticketRepository;
	private final UserRepository userRepository;      // Para buscar el usuario del ticket
	private final ProductRepository productRepository; // Para buscar los productos del ticket
	
	/**
	 * Constructor con inyección de dependencias.
	 * Se necesitan 3 repositorios porque un Ticket relaciona User y Products.
	 */
	public TicketServiceImpl(TicketRepository ticketRepository, 
	                         UserRepository userRepository,
	                         ProductRepository productRepository) {
		this.ticketRepository = ticketRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
	}
	
	/**
	 * Crea un nuevo ticket (el cual es un pedido).
	 * 
	 * @param ticketDto - DTO con los datos del pedido
	 * @return TicketDto - DTO del ticket guardado
	 */
	@Override
	public TicketDto save(TicketDto ticketDto) {
		// TODO: Aquí podrías agregar lógica de negocio como:
		// - Calcular el total basado en los productos
		// - Validar que el usuario existe
		// - Validar que los productos existen y tienen stock
		
		// Paso 1: Convertir DTO a Entity (busca user y products en BD)
		Ticket ticket = convertToEntity(ticketDto);
		
		// Paso 2: Guardar el ticket en la base de datos
		Ticket savedTicket = ticketRepository.save(ticket);
		
		// Paso 3: Convertir a DTO y devolver
		return convertToDto(savedTicket);
	}
	
	/**
	 * Busca un ticket por su ID.
	 * 
	 * @param id - ID del ticket
	 * @return TicketDto - DTO del ticket encontrado
	 */
	@Override
	public TicketDto findById(Long id) {
		// Buscar el ticket en la base de datos
		Optional<Ticket> optionalTicket = ticketRepository.findById(id);
		
		// Verificar si existe
		if (optionalTicket.isEmpty()) {
			throw new ResourceNotFoundException("Ticket no encontrado con id " + id);
		}
		
		// Convertir a DTO y devolver
		return convertToDto(optionalTicket.get());
	}
	
	/**
	 * Obtiene todos los tickets del sistema.
	 * 
	 * @return List<TicketDto> - Lista de todos los tickets
	 */
	@Override
	public List<TicketDto> findAll() {
		// Obtener todos los tickets y convertir cada uno a DTO
		return ticketRepository.findAll()
				.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}
	
	/**
	 * Actualiza un ticket existente.
	 * 
	 * @param id - ID del ticket a actualizar
	 * @param ticketDto - DTO con los nuevos datos
	 * @return TicketDto - DTO del ticket actualizado
	 */
	@Override
	public TicketDto update(Long id, TicketDto ticketDto) {
		// Paso 1: Verificar que el ticket existe
		Optional<Ticket> optionalTicket = ticketRepository.findById(id);
		if (optionalTicket.isEmpty()) {
			throw new ResourceNotFoundException("Ticket no encontrado con id " + id);
		}
		Ticket existingTicket = optionalTicket.get();
		
		// Paso 2: Actualizar los campos básicos
		existingTicket.setTicketDate(ticketDto.getTicketDate());
		existingTicket.setTotal(ticketDto.getTotal());
		existingTicket.setEventType(ticketDto.getEventType());
		
		// Paso 3: Actualizar el usuario si cambió
		if (ticketDto.getUserId() != null) {
		    User user = userRepository.findById(ticketDto.getUserId())
		            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id " + ticketDto.getUserId()));
		    existingTicket.setUser(user);
		}
		
		// Paso 4: Actualizar la lista de productos si cambió
		if (ticketDto.getProducts() != null) {
		    List<Product> products = ticketDto.getProducts().stream()
		            .map(productDto -> productRepository.findById(productDto.getIdentificador())
		                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id " + productDto.getIdentificador())))
		            .collect(Collectors.toList());
		    existingTicket.setProduct(products);
		}
		
		// Paso 5: Guardar los cambios
		Ticket updatedTicket = ticketRepository.save(existingTicket);
		
		// Paso 6: Convertir a DTO y devolver
		return convertToDto(updatedTicket);
	}
	
	/**
	 * Elimina un ticket (cancelar pedido).
	 * 
	 * @param id - ID del ticket a eliminar
	 */
	@Override
	public void deleteById(Long id) {
		// Verificar que existe antes de eliminar
		Optional<Ticket> optionalTicket = ticketRepository.findById(id);
		if (optionalTicket.isEmpty()) {
			throw new ResourceNotFoundException("Ticket no encontrado con id " + id);
		}
		
		// Eliminar de la base de datos
		ticketRepository.deleteById(id);
	}
	
	/**
	 * Obtiene todos los tickets de un usuario específico.
	 * Útil para mostrar el historial de pedidos del usuario.
	 * 
	 * @param userId - ID del usuario
	 * @return List<TicketDto> - Lista de tickets del usuario
	 */
	@Override
	public List<TicketDto> findByUserId(Long userId) {
	    return ticketRepository.findByUserUserId(userId)
	            .stream()
	            .map(this::convertToDto)
	            .collect(Collectors.toList());
	}
	
	// ========== Métodos privados de conversión ==========
	
	/**
	 * Convierte una Entity (Ticket) a DTO (TicketDto).
	 * 
	 * IMPORTANTE: Aquí también convertimos la lista de productos a ProductDto.
	 * Esto evita exponer las entities de Product directamente.
	 * 
	 * @param ticket - Entity de la base de datos
	 * @return TicketDto - DTO para enviar al controller
	 */
	private TicketDto convertToDto(Ticket ticket) {
		// Convertir la lista de productos (Entity) a lista de ProductDto
		List<ProductDto> productDtos = ticket.getProduct() != null 
			? ticket.getProduct().stream()
				.map(product -> new ProductDto(
					product.getProductId(),
					product.getProductName(),
					product.getCategory(),
					product.getProductPrice(),
					product.getProductDescription()
				))
				.collect(Collectors.toList())
			: null;  // Si no hay productos, devolver null
		
		// Crear el TicketDto con todos los datos
		return new TicketDto(
			ticket.getTicketId(),                                    // identificador
			ticket.getTicketDate(),
			ticket.getTotal(),
			ticket.getEventType(),
			ticket.getUser() != null ? ticket.getUser().getUserId() : null,  // Solo el ID del usuario
			productDtos                                               // Lista de ProductDto
		);
	}
	
	/**
	 * Convierte un DTO (TicketDto) a Entity (Ticket).
	 * 
	 * IMPORTANTE: En esta parte se busca el User y los Products en la BASE DE DATOS
	 * usando sus IDs, porque el DTO solo tiene los IDs.
	 * 
	 * @param dto - DTO recibido del controller
	 * @return Ticket - Entity para guardar en BD
	 */
	private Ticket convertToEntity(TicketDto dto) {
		Ticket ticket = new Ticket();
		
		// Asignar ID si existe (para updates)
		if (dto.getIdentificador() != null) {
			ticket.setTicketId(dto.getIdentificador());
		}
		
		// Asignar campos simples
		ticket.setTicketDate(dto.getTicketDate());
		ticket.setTotal(dto.getTotal());
		ticket.setEventType(dto.getEventType());
		
		// Buscar y asignar el usuario usando su ID
		if (dto.getUserId() != null) {
		    User user = userRepository.findById(dto.getUserId())
		            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id " + dto.getUserId()));
		    ticket.setUser(user);
		}
		
		// Buscar y asignar los productos usando sus IDs
		if (dto.getProducts() != null && !dto.getProducts().isEmpty()) {
		    List<Product> products = dto.getProducts().stream()
		            .map(productDto -> productRepository.findById(productDto.getIdentificador())
		                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id " + productDto.getIdentificador())))
		            .collect(Collectors.toList());
		    ticket.setProduct(products);
		}
		
		return ticket;
	}
}