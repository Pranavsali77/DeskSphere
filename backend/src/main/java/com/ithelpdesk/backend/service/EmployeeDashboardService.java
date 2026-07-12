package com.ithelpdesk.backend.service;

import com.ithelpdesk.backend.dto.DashboardResponse;

public interface EmployeeDashboardService {

    DashboardResponse getDashboard(String employeeEmail);

}