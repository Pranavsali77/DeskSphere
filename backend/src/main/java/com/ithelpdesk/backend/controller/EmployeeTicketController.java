package com.ithelpdesk.backend.controller;

import com.ithelpdesk.backend.dto.CloseTicketRequest;
import com.ithelpdesk.backend.dto.CreateTicketRequest;
import com.ithelpdesk.backend.dto.TicketResponse;
import com.ithelpdesk.backend.dto.UpdateTicketRequest;
import com.ithelpdesk.backend.service.EmployeeTicketService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "*")
public class EmployeeTicketController {

    private final EmployeeTicketService employeeTicketService;

    public EmployeeTicketController(EmployeeTicketService employeeTicketService) {
        this.employeeTicketService = employeeTicketService;
    }
    @PostMapping("/tickets")
    public ResponseEntity<TicketResponse> createTicket(
            @Valid @RequestBody CreateTicketRequest request,
            @RequestParam String email) {

        return ResponseEntity.ok(
                employeeTicketService.createTicket(request, email)
        );
    }
    @GetMapping("/tickets")
    public ResponseEntity<List<TicketResponse>> getMyTickets(
            @RequestParam String email) {

        return ResponseEntity.ok(
                employeeTicketService.getMyTickets(email)
        );
    }
    @GetMapping("/tickets/{ticketId}")
    public ResponseEntity<TicketResponse> getTicketById(
            @PathVariable Long ticketId,
            @RequestParam String email) {

        return ResponseEntity.ok(
                employeeTicketService.getTicketById(ticketId, email)
        );
    }
    @PutMapping("/tickets/{ticketId}")
    public ResponseEntity<TicketResponse> updateTicket(
            @PathVariable Long ticketId,
            @Valid @RequestBody UpdateTicketRequest request,
            @RequestParam String email) {

        return ResponseEntity.ok(
                employeeTicketService.updateTicket(ticketId, request, email)
        );
    }
    @DeleteMapping("/tickets/{ticketId}")
    public ResponseEntity<String> deleteTicket(
            @PathVariable Long ticketId,
            @RequestParam String email) {

        return ResponseEntity.ok(
                employeeTicketService.deleteTicket(ticketId, email)
        );
    }

    @PutMapping("/tickets/{ticketId}/close")
    public ResponseEntity<TicketResponse> closeTicket(
        @PathVariable Long ticketId,
        @Valid @RequestBody CloseTicketRequest request,
        @RequestParam String email) {

         return ResponseEntity.ok(
            employeeTicketService.closeTicket(ticketId, request, email)
         );
       }

       @PutMapping("/tickets/{ticketId}/reopen")
        public ResponseEntity<TicketResponse> reopenTicket(
                @PathVariable Long ticketId,
                @RequestParam String email) {

         return ResponseEntity.ok(
            employeeTicketService.reopenTicket(ticketId, email)
        );
        }
}