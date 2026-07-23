package com.ithelpdesk.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ithelpdesk.backend.dto.CloseTicketRequest;
import com.ithelpdesk.backend.dto.CreateTicketRequest;
import com.ithelpdesk.backend.dto.TicketResponse;
import com.ithelpdesk.backend.dto.UpdateTicketRequest;
import com.ithelpdesk.backend.entity.Department;
import com.ithelpdesk.backend.entity.Ticket;
import com.ithelpdesk.backend.entity.User;
import com.ithelpdesk.backend.enums.TicketStatus;
import com.ithelpdesk.backend.repository.DepartmentRepository;
import com.ithelpdesk.backend.repository.TicketRepository;
import com.ithelpdesk.backend.repository.UserRepository;

@Service
@Transactional
public class EmployeeTicketServiceImpl implements EmployeeTicketService {
	   private final TicketRepository ticketRepository;
	    private final UserRepository userRepository;
	    private final DepartmentRepository departmentRepository;

		private final TicketActivityService ticketActivityService;

	    // ============================
	    // Constructor
	    // ============================

	   public EmployeeTicketServiceImpl(
        TicketRepository ticketRepository,
        UserRepository userRepository,
        DepartmentRepository departmentRepository,
        TicketActivityService ticketActivityService) {

    this.ticketRepository = ticketRepository;
    this.userRepository = userRepository;
    this.departmentRepository = departmentRepository;
    this.ticketActivityService = ticketActivityService;
}

	    //  generateTicketNumber() 

	    private String generateTicketNumber() {

	        return "TKT-" + UUID.randomUUID()
	                .toString()
	                .substring(0, 8)
	                .toUpperCase();
	    }

	    //  mapToResponse() 

	    private TicketResponse mapToResponse(Ticket ticket) {

	        TicketResponse response = new TicketResponse();

	        response.setId(ticket.getId());
	        response.setTicketNumber(ticket.getTicketNumber());
	        response.setTitle(ticket.getTitle());
	        response.setDescription(ticket.getDescription());

	        response.setEmployeeName(
	                ticket.getEmployee().getFirstName() + " "
	                + ticket.getEmployee().getLastName());

	        if (ticket.getEngineer() != null) {
	            response.setEngineerName(
	                    ticket.getEngineer().getFirstName() + " "
	                    + ticket.getEngineer().getLastName());
	        }

	        response.setDepartment(
	                ticket.getDepartment().getDepartmentName());

	        response.setCategory(ticket.getCategory().name());
	        response.setPriority(ticket.getPriority().name());
	        response.setStatus(ticket.getStatus().name());

	        response.setResolution(ticket.getResolution());

	        response.setCreatedAt(ticket.getCreatedAt());
	        response.setUpdatedAt(ticket.getUpdatedAt());
	        response.setResolvedAt(ticket.getResolvedAt());

	        return response;
	    }

	    @Override
	    public TicketResponse createTicket(CreateTicketRequest request,
	                                       String employeeEmail) {

	        // Find Employee
	        User employee = userRepository.findByEmail(employeeEmail)
	                .orElseThrow(() ->
	                        new RuntimeException("Employee not found"));

	        // Find Department
	        Department department = departmentRepository.findById(request.getDepartmentId())
	                .orElseThrow(() ->
	                        new RuntimeException("Department not found"));

	        // Create Ticket
	        Ticket ticket = new Ticket();

	        ticket.setTicketNumber(generateTicketNumber());
	        ticket.setTitle(request.getTitle());
	        ticket.setDescription(request.getDescription());

	        ticket.setEmployee(employee);
	        ticket.setDepartment(department);

	        ticket.setCategory(request.getCategory());
	        ticket.setPriority(request.getPriority());

	        ticket.setStatus(TicketStatus.OPEN);

			Ticket savedTicket = ticketRepository.save(ticket);

	ticketActivityService.logActivity(
        savedTicket,
        "TICKET_CREATED",
        "Ticket created successfully",
        employee.getEmail()
	);

	return mapToResponse(savedTicket);		

	    }

	    @Override
	    public List<TicketResponse> getMyTickets(String employeeEmail) {

	        // Find Employee
	        User employee = userRepository.findByEmail(employeeEmail)
	                .orElseThrow(() ->
	                        new RuntimeException("Employee not found"));

	        // Get Employee Tickets
	        List<Ticket> tickets = ticketRepository.findByEmployee(employee);

	        // Convert Entity List to DTO List
	        return tickets.stream()
	                .map(this::mapToResponse)
	                .collect(Collectors.toList());
	    }

	    @Override
	    public TicketResponse getTicketById(Long ticketId,
	                                        String employeeEmail) {

	        // Find logged-in employee
	        User employee = userRepository.findByEmail(employeeEmail)
	                .orElseThrow(() ->
	                        new RuntimeException("Employee not found"));

	        // Find ticket belonging to this employee
	        Ticket ticket = ticketRepository
	                .findByIdAndEmployee(ticketId, employee)
	                .orElseThrow(() ->
	                        new RuntimeException("Ticket not found"));

	        // Convert Entity to DTO
	        return mapToResponse(ticket);
	    }

    @Override
    public TicketResponse updateTicket(Long ticketId,
                                       UpdateTicketRequest request,
                                       String employeeEmail) {

        // Find Employee
        User employee = userRepository.findByEmail(employeeEmail)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        // Find Ticket
        Ticket ticket = ticketRepository.findByIdAndEmployee(ticketId, employee)
                .orElseThrow(() ->
                        new RuntimeException("Ticket not found"));

        // Only OPEN tickets can be updated
        if (ticket.getStatus() != TicketStatus.OPEN) {
            throw new RuntimeException("Only OPEN tickets can be updated.");
        }

        // Update fields
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setCategory(request.getCategory());
        ticket.setPriority(request.getPriority());

        // Update Department if changed
        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new RuntimeException("Department not found"));

        ticket.setDepartment(department);

        // Save
        Ticket updatedTicket = ticketRepository.save(ticket);

	ticketActivityService.logActivity(
        updatedTicket,
        "TICKET_UPDATED",
        "Ticket updated successfully",
        employee.getEmail()
	);

	return mapToResponse(updatedTicket);

    }

    @Override
    public String deleteTicket(Long ticketId,
                               String employeeEmail) {

        // Find Employee
        User employee = userRepository.findByEmail(employeeEmail)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        // Find Ticket
        Ticket ticket = ticketRepository
                .findByIdAndEmployee(ticketId, employee)
                .orElseThrow(() ->
                        new RuntimeException("Ticket not found"));

        // Allow delete only when ticket is OPEN
        if (ticket.getStatus() != TicketStatus.OPEN) {
            throw new RuntimeException("Only OPEN tickets can be deleted.");
        }

        // Delete Ticket
        ticketRepository.delete(ticket);

        return "Ticket deleted successfully.";
    }


	@Override
	public TicketResponse closeTicket(Long ticketId,
                                  CloseTicketRequest request,
                                  String employeeEmail) {

    User employee = userRepository.findByEmail(employeeEmail)
            .orElseThrow(() ->
                    new RuntimeException("Employee not found"));

    Ticket ticket = ticketRepository
            .findByIdAndEmployee(ticketId, employee)
            .orElseThrow(() ->
                    new RuntimeException("Ticket not found"));

    if (ticket.getStatus() == TicketStatus.CLOSED) {
        throw new RuntimeException("Ticket is already closed.");
    }

    ticket.setStatus(TicketStatus.CLOSED);
    ticket.setResolution(request.getResolution());
    ticket.setResolvedAt(LocalDateTime.now());

   Ticket updatedTicket = ticketRepository.save(ticket);

	ticketActivityService.logActivity(
        updatedTicket,
        "TICKET_CLOSED",
        "Ticket closed by employee",
        employee.getEmail()
	);

	return mapToResponse(updatedTicket);
	}

	@Override
public TicketResponse reopenTicket(Long ticketId,
                                   String employeeEmail) {

    User employee = userRepository.findByEmail(employeeEmail)
            .orElseThrow(() ->
                    new RuntimeException("Employee not found"));

    Ticket ticket = ticketRepository
            .findByIdAndEmployee(ticketId, employee)
            .orElseThrow(() ->
                    new RuntimeException("Ticket not found"));

    if (ticket.getStatus() != TicketStatus.CLOSED) {
        throw new RuntimeException("Only CLOSED tickets can be reopened.");
    }

    ticket.setStatus(TicketStatus.OPEN);
    ticket.setResolution(null);
    ticket.setResolvedAt(null);

    Ticket updated = ticketRepository.save(ticket);

	ticketActivityService.logActivity(
        updated,
        "TICKET_REOPENED",
        "Ticket reopened by employee",
        employee.getEmail()
	);

	return mapToResponse(updated);

}

}