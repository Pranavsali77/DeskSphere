package com.ithelpdesk.backend.dto;

public class AIIntentResponse {

    private String category;
    private String priority;
    private String department;
    private boolean ticketRequired;
    private String reason;

    public AIIntentResponse() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isTicketRequired() {
        return ticketRequired;
    }

    public void setTicketRequired(boolean ticketRequired) {
        this.ticketRequired = ticketRequired;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}