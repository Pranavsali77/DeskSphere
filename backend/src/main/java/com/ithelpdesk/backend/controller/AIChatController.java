package com.ithelpdesk.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ithelpdesk.backend.dto.AIChatRequest;
import com.ithelpdesk.backend.dto.AIChatResponse;
import com.ithelpdesk.backend.service.AIChatService;

@RestController
@RequestMapping("/api/ai")
public class AIChatController {

    private final AIChatService aiChatService;

    public AIChatController(AIChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    @PostMapping("/chat")
    public ResponseEntity<AIChatResponse> chat(
            @RequestBody AIChatRequest request) {

        return ResponseEntity.ok(
                aiChatService.chat(request)
        );
    }
}