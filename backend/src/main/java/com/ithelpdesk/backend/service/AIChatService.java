package com.ithelpdesk.backend.service;

import com.ithelpdesk.backend.dto.AIChatRequest;
import com.ithelpdesk.backend.dto.AIChatResponse;
import com.ithelpdesk.backend.dto.AIIntentRequest;
import com.ithelpdesk.backend.dto.AIIntentResponse;

public interface AIChatService {

    AIChatResponse chat(AIChatRequest request);

    AIIntentResponse detectIntent(AIIntentRequest request);
}