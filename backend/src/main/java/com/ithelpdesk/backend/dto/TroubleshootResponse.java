package com.ithelpdesk.backend.dto;

public class TroubleshootResponse {

    private String solution;

    public TroubleshootResponse() {
    }

    public TroubleshootResponse(String solution) {
        this.solution = solution;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}