package com.ithelpdesk.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ithelpdesk.backend.dto.NotificationResponse;
import com.ithelpdesk.backend.service.EmployeeNotificationService;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "*")
public class EmployeeNotificationController {

    private final EmployeeNotificationService notificationService;

    public EmployeeNotificationController(
            EmployeeNotificationService notificationService) {

        this.notificationService = notificationService;
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationResponse>> getNotifications(
            @RequestParam String email) {

        return ResponseEntity.ok(
                notificationService.getNotifications(email)
        );
    }

    @PutMapping("/notifications/{notificationId}/read")
    public ResponseEntity<String> markAsRead(
            @PathVariable Long notificationId,
            @RequestParam String email) {

        return ResponseEntity.ok(
                notificationService.markAsRead(notificationId, email)
        );
    }

    @DeleteMapping("/notifications/{notificationId}")
    public ResponseEntity<String> deleteNotification(
            @PathVariable Long notificationId,
            @RequestParam String email) {

        return ResponseEntity.ok(
                notificationService.deleteNotification(notificationId, email)
        );
    }
}