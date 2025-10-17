package com.pastiara.app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pastiara.app.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long>{
	
	//===========Métodos personalizados==============================
	
	// Buscar tickets por usuario
    List<Ticket> findByUserId(Long userId);

    // Buscar tickets emitidos dentro de un rango de fechas
    List<Ticket> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

}
