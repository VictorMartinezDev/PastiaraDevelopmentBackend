package com.pastiara.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pastiara.app.dto.TicketDto;
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
            return ResponseEntity.ok(ticket);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // GET - Obtener tickets por usuario (para ver historial de pedidos)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketDto>> getTicketsByUserId(@PathVariable Long userId) {
        try {
            List<TicketDto> tickets = ticketService.findByUserId(userId);
            return ResponseEntity.ok(tickets);
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // POST - Crear nuevo ticket (cuando el usuario hace un pedido)
    @PostMapping
    public ResponseEntity<TicketDto> createTicket(@RequestBody TicketDto ticketDto) {
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
            return ResponseEntity.ok(updatedTicket);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // DELETE - Eliminar ticket (cancelar pedido)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        try {
            ticketService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
}