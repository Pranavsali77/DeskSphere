package com.ithelpdesk.backend.controller;

import com.ithelpdesk.backend.dto.AIIntentRequest;
import com.ithelpdesk.backend.dto.AIIntentResponse;
import com.ithelpdesk.backend.service.AIChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIIntentController {

    private final AIChatService aiChatService;

    public AIIntentController(AIChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    @PostMapping("/intent")
    public ResponseEntity<AIIntentResponse> detectIntent(
            @RequestBody AIIntentRequest request) {

        return ResponseEntity.ok(
                aiChatService.detectIntent(request)
        );
    }
}