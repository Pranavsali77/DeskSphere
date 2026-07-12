package com.ithelpdesk.backend.controller;

import com.ithelpdesk.backend.dto.AutoTicketRequest;
import com.ithelpdesk.backend.dto.AutoTicketResponse;
import com.ithelpdesk.backend.service.AIAutoTicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIAutoTicketController {

    private final AIAutoTicketService autoTicketService;

    public AIAutoTicketController(AIAutoTicketService autoTicketService) {
        this.autoTicketService = autoTicketService;
    }

    @PostMapping("/auto-ticket")
    public ResponseEntity<AutoTicketResponse> createTicket(
            @RequestBody AutoTicketRequest request) {

        AutoTicketResponse response =
                autoTicketService.createAutoTicket(request);

        return ResponseEntity.ok(response);
    }

}