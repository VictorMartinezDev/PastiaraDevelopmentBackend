
package com.pastiara.app.service1;

import org.springframework.stereotype.Service;

import com.pastiara.app.model.Ticket;
import com.pastiara.app.repository.TicketRepository;

import java.util.List;

@Service
public class TicketService {

	private final TicketRepository ticketRepository;

	public TicketService(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	// Crear o actualizar tickets
	public Ticket save(Ticket ticket) {
		return ticketRepository.save(ticket);
	}

	// Obtener todos los tickets
	public List<Ticket> findAll() {
		return ticketRepository.findAll();
	}

	// Buscar un ticket por ID
	public Ticket findById(Long id) {
		return ticketRepository.findById(id).orElse(null);
	}
	
	// Eliminar ticket
	public void delete(Long id) {
		ticketRepository.deleteById(id);
	}
}
