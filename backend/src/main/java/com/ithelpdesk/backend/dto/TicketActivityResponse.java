package com.ithelpdesk.backend.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketActivityResponse {

    private String action;
    private String description;
    private String performedBy;
    private LocalDateTime performedAt;

}