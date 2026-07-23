package com.ithelpdesk.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ithelpdesk.backend.dto.CloseTicketRequest;
import com.ithelpdesk.backend.dto.CreateTicketRequest;
import com.ithelpdesk.backend.dto.TicketActivityResponse;
import com.ithelpdesk.backend.dto.TicketResponse;
import com.ithelpdesk.backend.dto.UpdateTicketRequest;
import com.ithelpdesk.backend.service.EmployeeTicketService;
import com.ithelpdesk.backend.service.TicketActivityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "*")
public class EmployeeTicketController {

    private final EmployeeTicketService employeeTicketService;
    private final TicketActivityService ticketActivityService;

    public EmployeeTicketController(
            EmployeeTicketService employeeTicketService,
            TicketActivityService ticketActivityService) {

        this.employeeTicketService = employeeTicketService;
        this.ticketActivityService = ticketActivityService;
    }

    // ===============================
    // Create Ticket
    // ===============================
    @PostMapping("/tickets")
    public ResponseEntity<TicketResponse> createTicket(
            @Valid @RequestBody CreateTicketRequest request,
            @RequestParam String email) {

        return ResponseEntity.ok(
                employeeTicketService.createTicket(request, email));
    }

    // ===============================
    // Get My Tickets
    // ===============================
    @GetMapping("/tickets")
    public ResponseEntity<List<TicketResponse>> getMyTickets(
            @RequestParam String email) {

        return ResponseEntity.ok(
                employeeTicketService.getMyTickets(email));
    }

    // ===============================
    // Get Ticket By Id
    // ===============================
    @GetMapping("/tickets/{ticketId}")
    public ResponseEntity<TicketResponse> getTicketById(
            @PathVariable Long ticketId,
            @RequestParam String email) {

        return ResponseEntity.ok(
                employeeTicketService.getTicketById(ticketId, email));
    }

    // ===============================
    // Update Ticket
    // ===============================
    @PutMapping("/tickets/{ticketId}")
    public ResponseEntity<TicketResponse> updateTicket(
            @PathVariable Long ticketId,
            @Valid @RequestBody UpdateTicketRequest request,
            @RequestParam String email) {

        return ResponseEntity.ok(
                employeeTicketService.updateTicket(ticketId, request, email));
    }

    // ===============================
    // Delete Ticket
    // ===============================
    @DeleteMapping("/tickets/{ticketId}")
    public ResponseEntity<String> deleteTicket(
            @PathVariable Long ticketId,
            @RequestParam String email) {

        return ResponseEntity.ok(
                employeeTicketService.deleteTicket(ticketId, email));
    }

    // ===============================
    // Close Ticket
    // ===============================
    @PutMapping("/tickets/{ticketId}/close")
    public ResponseEntity<TicketResponse> closeTicket(
            @PathVariable Long ticketId,
            @Valid @RequestBody CloseTicketRequest request,
            @RequestParam String email) {

        return ResponseEntity.ok(
                employeeTicketService.closeTicket(ticketId, request, email));
    }

    // ===============================
    // Reopen Ticket
    // ===============================
    @PutMapping("/tickets/{ticketId}/reopen")
    public ResponseEntity<TicketResponse> reopenTicket(
            @PathVariable Long ticketId,
            @RequestParam String email) {

        return ResponseEntity.ok(
                employeeTicketService.reopenTicket(ticketId, email));
    }

    // ===============================
    // Ticket Timeline
    // ===============================
    @GetMapping("/tickets/{ticketId}/timeline")
    public ResponseEntity<List<TicketActivityResponse>> getTimeline(
            @PathVariable Long ticketId) {

        return ResponseEntity.ok(
                ticketActivityService.getTimeline(ticketId));
    }

}