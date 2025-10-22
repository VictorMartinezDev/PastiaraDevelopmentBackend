package com.pastiara.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pastiara.app.dto.TicketDto;
import com.pastiara.app.exception.BadRequestException;
import com.pastiara.app.exception.ResourceNotFoundException;
import com.pastiara.app.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
public class TicketController {
    
    private final TicketService ticketService;
    
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    
    // GET - Obtener todos los tickets 
    @GetMapping
    public ResponseEntity<List<TicketDto>> getAllTickets() {
        List<TicketDto> tickets = ticketService.findAll();
        return ResponseEntity.ok(tickets);
    }
    
    // GET - Obtener ticket por ID
    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getTicketById(@PathVariable Long id) {
        try {
            TicketDto ticket = ticketService.findById(id);
            if (ticket == null) {
                throw new ResourceNotFoundException("No se encontró el ticket con ID: " + id);
            }
            return ResponseEntity.ok(ticket);
        } finally {
            System.out.println("Solicitud procesada para ticket ID: " + id);
        }
    }
    
    // GET - Obtener tickets por usuario (para ver historial de pedidos)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketDto>> getTicketsByUserId(@PathVariable Long userId) {
        try {
            List<TicketDto> tickets = ticketService.findByUserId(userId);
            if (tickets == null || tickets.isEmpty()) {
                throw new ResourceNotFoundException("No se encontraron tickets para el usuario ID: " + userId);
            }
            return ResponseEntity.ok(tickets);
        } finally {
            System.out.println("Solicitud procesada para usuario ID: " + userId);
        }
    }
    
    // POST - Crear nuevo ticket (cuando el usuario hace un pedido)
    @PostMapping
    public ResponseEntity<TicketDto> createTicket(@RequestBody TicketDto ticketDto) {
        if (ticketDto == null) {
            throw new BadRequestException("Los datos del ticket no pueden estar vacíos");
        }
        TicketDto savedTicket = ticketService.save(ticketDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTicket);
    }
    
    // PUT - Actualizar ticket existente
    @PutMapping("/{id}")
    public ResponseEntity<TicketDto> updateTicket(
            @PathVariable Long id,
            @RequestBody TicketDto ticketDto) {
        try {
            TicketDto updatedTicket = ticketService.update(id, ticketDto);
            if (updatedTicket == null) {
                throw new ResourceNotFoundException("No se encontró el ticket con ID: " + id + " para actualizar");
            }
            return ResponseEntity.ok(updatedTicket);
        } finally {
            System.out.println("Solicitud de actualización procesada para ticket ID: " + id);
        }
    }
    
    // DELETE - Eliminar ticket (cancelar pedido)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        try {
            ticketService.deleteById(id); // sigue siendo void
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) { // si el ticket no existe
            throw new ResourceNotFoundException("No se encontró el ticket con ID: " + id + " para eliminar");
        } finally {
            System.out.println("Solicitud de eliminación procesada para ticket ID: " + id);
        }
    }
}