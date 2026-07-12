package com.ithelpdesk.backend.service;

import com.ithelpdesk.backend.dto.AutoTicketRequest;
import com.ithelpdesk.backend.dto.AutoTicketResponse;

public interface AIAutoTicketService {

    AutoTicketResponse createAutoTicket(AutoTicketRequest request);

}