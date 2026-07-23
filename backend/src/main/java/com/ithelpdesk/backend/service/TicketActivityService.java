package com.ithelpdesk.backend.service;

import java.util.List;

import com.ithelpdesk.backend.dto.TicketActivityResponse;
import com.ithelpdesk.backend.entity.Ticket;

public interface TicketActivityService {

    void logActivity(
            Ticket ticket,
            String action,
            String description,
            String performedBy
    );

    List<TicketActivityResponse> getTimeline(Long ticketId);
}