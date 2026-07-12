package com.ithelpdesk.backend.dto;

public class AutoTicketResponse {

    private String title;
    private String description;
    private String category;
    private String priority;
    private String department;
    private boolean ticketRequired;

    public AutoTicketResponse() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}