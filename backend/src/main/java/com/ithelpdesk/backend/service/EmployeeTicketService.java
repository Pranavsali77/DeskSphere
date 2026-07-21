package com.ithelpdesk.backend.service;

import java.util.List;

import com.ithelpdesk.backend.dto.CloseTicketRequest;
import com.ithelpdesk.backend.dto.CreateTicketRequest;
import com.ithelpdesk.backend.dto.TicketResponse;
import com.ithelpdesk.backend.dto.UpdateTicketRequest;



public interface EmployeeTicketService {

    // Create Ticket
    TicketResponse createTicket(CreateTicketRequest request, String employeeEmail);

    // View All My Tickets
    List<TicketResponse> getMyTickets(String employeeEmail);

    // View Ticket By Id
    TicketResponse getTicketById(Long ticketId, String employeeEmail);

    // Update Ticket
    TicketResponse updateTicket(Long ticketId,
                                UpdateTicketRequest request,
                                String employeeEmail);

    // Delete Ticket
    String deleteTicket(Long ticketId, String employeeEmail);

    TicketResponse closeTicket(Long ticketId,
                           CloseTicketRequest request,
                           String employeeEmail);

    TicketResponse reopenTicket(Long ticketId, String employeeEmail);                       

}