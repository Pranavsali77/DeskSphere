package com.ithelpdesk.backend.service;

import com.ithelpdesk.backend.dto.TroubleshootRequest;
import com.ithelpdesk.backend.dto.TroubleshootResponse;

public interface AITroubleshootingService {

    TroubleshootResponse troubleshoot(TroubleshootRequest request);

}