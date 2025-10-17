package com.pastiara.app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pastiara.app.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long>{
	
	//===========Métodos personalizados==============================
	
	// Buscar tickets por usuario
    List<Ticket> findByUserUserId(Long userId);

    // Buscar tickets emitidos dentro de un rango de fechas
    List<Ticket> findByTicketDateBetween(LocalDate start, LocalDate end);

}
