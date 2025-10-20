package com.pastiara.app.service;

import com.pastiara.app.dto.TicketDto;
import java.util.List;

public interface TicketService {
	
	TicketDto save(TicketDto ticketDto);
	
	TicketDto findById(Long id);
	
	List<TicketDto> findAll();
	
	TicketDto update(Long id, TicketDto ticketDto);
	
	void deleteById(Long id);
	
	// Método adicional: Obtener tickets por usuario
	List<TicketDto> findByUserId(Long userId);
	
}
