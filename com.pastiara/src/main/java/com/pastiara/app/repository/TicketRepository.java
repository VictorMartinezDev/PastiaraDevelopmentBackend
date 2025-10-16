package com.pastiara.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pastiara.app.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long>{

}
