package com.ithelpdesk.backend.service;

import com.ithelpdesk.backend.dto.AutoTicketRequest;
import com.ithelpdesk.backend.dto.AutoTicketResponse;
import com.ithelpdesk.backend.entity.Ticket;
import com.ithelpdesk.backend.repository.TicketRepository;
import org.springframework.stereotype.Service;
import com.ithelpdesk.backend.enums.TicketCategory;
import com.ithelpdesk.backend.enums.TicketPriority;
import com.ithelpdesk.backend.enums.TicketStatus;
import org.springframework.web.client.RestTemplate;

@Service
public class AIAutoTicketServiceImpl implements AIAutoTicketService {

    private final RestTemplate restTemplate;
    private final TicketRepository ticketRepository;

    private static final String PYTHON_URL =
            "http://localhost:8000/auto-ticket";

    public AIAutoTicketServiceImpl(RestTemplate restTemplate,
                                   TicketRepository ticketRepository) {

        this.restTemplate = restTemplate;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public AutoTicketResponse createAutoTicket(AutoTicketRequest request) {

        // Call Python AI Service
        AutoTicketResponse aiResponse =
                restTemplate.postForObject(
                        PYTHON_URL,
                        request,
                        AutoTicketResponse.class
                );

        if (aiResponse == null) {
            throw new RuntimeException("AI Service not responding.");
        }

        // Save ticket only if AI recommends it
        if (aiResponse.isTicketRequired()) {

            Ticket ticket = new Ticket();

            ticket.setTitle(aiResponse.getTitle());
            ticket.setDescription(aiResponse.getDescription());
            ticket.setCategory(
            	    TicketCategory.valueOf(aiResponse.getCategory().toUpperCase())
            	);

            	ticket.setPriority(
            	    TicketPriority.valueOf(aiResponse.getPriority().toUpperCase())
            	);

            	ticket.setStatus(TicketStatus.OPEN);

            ticketRepository.save(ticket);
        }

        return aiResponse;
    }
}