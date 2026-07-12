package com.ithelpdesk.backend.dto;

public class TroubleshootRequest {

    private String message;

    public TroubleshootRequest() {
    }

    public TroubleshootRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}