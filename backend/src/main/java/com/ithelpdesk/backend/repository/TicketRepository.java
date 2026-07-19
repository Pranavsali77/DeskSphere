package com.ithelpdesk.backend.repository;

import com.ithelpdesk.backend.entity.Ticket;
import com.ithelpdesk.backend.entity.User;
import com.ithelpdesk.backend.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Employee Tickets
    List<Ticket> findByEmployee(User employee);

    // Engineer Tickets
    List<Ticket> findByEngineer(User engineer);

    // Department Tickets
    List<Ticket> findByDepartmentId(Long departmentId);

    // Status Wise Tickets
    List<Ticket> findByStatus(TicketStatus status);

    // Employee + Status
    List<Ticket> findByEmployeeAndStatus(User employee, TicketStatus status);

    // Search Ticket
    Ticket findByTicketNumber(String ticketNumber);
    
    //Get Ticket By ID
    Optional<Ticket> findByIdAndEmployee(Long id, User employee);

    long countByEmployee(User employee);

    long countByEmployeeAndStatus(User employee, TicketStatus status);

    List<Ticket> findTop5ByEmployeeOrderByCreatedAtDesc(User employee);
}