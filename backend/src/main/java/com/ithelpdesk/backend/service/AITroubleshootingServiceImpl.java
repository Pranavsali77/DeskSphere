package com.ithelpdesk.backend.service;

import com.ithelpdesk.backend.dto.TroubleshootRequest;
import com.ithelpdesk.backend.dto.TroubleshootResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AITroubleshootingServiceImpl implements AITroubleshootingService {

    private final RestTemplate restTemplate;

    private static final String PYTHON_URL =
            "http://localhost:8000/troubleshoot";

    public AITroubleshootingServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public TroubleshootResponse troubleshoot(TroubleshootRequest request) {

        return restTemplate.postForObject(
                PYTHON_URL,
                request,
                TroubleshootResponse.class
        );
    }
}