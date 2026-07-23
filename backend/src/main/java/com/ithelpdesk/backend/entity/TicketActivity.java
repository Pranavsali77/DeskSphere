package com.ithelpdesk.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ticket_activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;

    @Column(length = 1000)
    private String description;

    private String performedBy;

    private LocalDateTime performedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @PrePersist
    public void onCreate() {
        this.performedAt = LocalDateTime.now();
    }
}