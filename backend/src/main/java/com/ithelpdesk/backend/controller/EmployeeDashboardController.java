package com.ithelpdesk.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ithelpdesk.backend.dto.DashboardResponse;
import com.ithelpdesk.backend.service.EmployeeDashboardService;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "*")
public class EmployeeDashboardController {

    private final EmployeeDashboardService dashboardService;

    public EmployeeDashboardController(EmployeeDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard(
            @RequestParam String email) {

        return ResponseEntity.ok(
                dashboardService.getDashboard(email)
        );
    }
}