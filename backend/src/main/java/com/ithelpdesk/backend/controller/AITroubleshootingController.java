package com.ithelpdesk.backend.controller;

import com.ithelpdesk.backend.dto.TroubleshootRequest;
import com.ithelpdesk.backend.dto.TroubleshootResponse;
import com.ithelpdesk.backend.service.AITroubleshootingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AITroubleshootingController {

    private final AITroubleshootingService service;

    public AITroubleshootingController(AITroubleshootingService service) {
        this.service = service;
    }

    @PostMapping("/troubleshoot")
    public ResponseEntity<TroubleshootResponse> troubleshoot(
            @RequestBody TroubleshootRequest request) {

        return ResponseEntity.ok(service.troubleshoot(request));
    }
}