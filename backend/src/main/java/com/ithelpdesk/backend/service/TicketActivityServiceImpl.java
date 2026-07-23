package com.ithelpdesk.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ithelpdesk.backend.dto.TicketActivityResponse;
import com.ithelpdesk.backend.entity.Ticket;
import com.ithelpdesk.backend.entity.TicketActivity;
import com.ithelpdesk.backend.repository.TicketActivityRepository;

@Service
public class TicketActivityServiceImpl implements TicketActivityService {

    private final TicketActivityRepository ticketActivityRepository;

    public TicketActivityServiceImpl(
            TicketActivityRepository ticketActivityRepository) {

        this.ticketActivityRepository = ticketActivityRepository;
    }

    @Override
    public void logActivity(
            Ticket ticket,
            String action,
            String description,
            String performedBy) {

        TicketActivity activity = TicketActivity.builder()
                .ticket(ticket)
                .action(action)
                .description(description)
                .performedBy(performedBy)
                .build();

        ticketActivityRepository.save(activity);
    }

    @Override
    public List<TicketActivityResponse> getTimeline(Long ticketId) {

        return ticketActivityRepository
                .findByTicketIdOrderByPerformedAtAsc(ticketId)
                .stream()
                .map(activity -> {

                    TicketActivityResponse response =
                            new TicketActivityResponse();

                    response.setAction(activity.getAction());
                    response.setDescription(activity.getDescription());
                    response.setPerformedBy(activity.getPerformedBy());
                    response.setPerformedAt(activity.getPerformedAt());

                    return response;

                })
                .collect(Collectors.toList());
    }
}