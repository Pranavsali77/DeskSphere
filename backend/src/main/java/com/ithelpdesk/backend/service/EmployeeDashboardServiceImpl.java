package com.ithelpdesk.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ithelpdesk.backend.dto.DashboardResponse;
import com.ithelpdesk.backend.dto.TicketResponse;
import com.ithelpdesk.backend.enums.TicketStatus;
import com.ithelpdesk.backend.repository.TicketRepository;
import com.ithelpdesk.backend.repository.UserRepository;
import com.ithelpdesk.backend.entity.Ticket;
import com.ithelpdesk.backend.entity.User;

@Service
public class EmployeeDashboardServiceImpl implements EmployeeDashboardService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    // ==========================
    // Constructor
    // ==========================

    public EmployeeDashboardServiceImpl(
            TicketRepository ticketRepository,
            UserRepository userRepository) {

        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    // ==========================
    // Paste mapToResponse() HERE
    // ==========================

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

        response.setDepartment(ticket.getDepartment().getDepartmentName());

        response.setCategory(ticket.getCategory().name());
        response.setPriority(ticket.getPriority().name());
        response.setStatus(ticket.getStatus().name());

        response.setResolution(ticket.getResolution());

        response.setCreatedAt(ticket.getCreatedAt());
        response.setUpdatedAt(ticket.getUpdatedAt());
        response.setResolvedAt(ticket.getResolvedAt());

        return response;
    }

    // ==========================
    // getDashboard()
    // ==========================

    @Override
    public DashboardResponse getDashboard(String employeeEmail) {

        // Find Employee
        User employee = userRepository.findByEmail(employeeEmail)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        // Count Tickets
        long totalTickets =
                ticketRepository.countByEmployee(employee);

        long openTickets =
                ticketRepository.countByEmployeeAndStatus(employee, TicketStatus.OPEN);

        long inProgressTickets =
                ticketRepository.countByEmployeeAndStatus(employee, TicketStatus.IN_PROGRESS);

        long resolvedTickets =
                ticketRepository.countByEmployeeAndStatus(employee, TicketStatus.RESOLVED);

        long closedTickets =
                ticketRepository.countByEmployeeAndStatus(employee, TicketStatus.CLOSED);

        // Recent Tickets
        List<TicketResponse> recentTickets =
                ticketRepository.findTop5ByEmployeeOrderByCreatedAtDesc(employee)
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        // Create Dashboard Response
        DashboardResponse response = new DashboardResponse();

        response.setEmployeeName(
                employee.getFirstName() + " " + employee.getLastName());

        response.setTotalTickets(totalTickets);
        response.setOpenTickets(openTickets);
        response.setInProgressTickets(inProgressTickets);
        response.setResolvedTickets(resolvedTickets);
        response.setClosedTickets(closedTickets);
        response.setRecentTickets(recentTickets);

        return response;
    }
}