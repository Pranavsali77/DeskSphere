package com.ithelpdesk.backend.dto;

public class AutoTicketRequest {

    private String message;

    public AutoTicketRequest() {
    }

    public AutoTicketRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}