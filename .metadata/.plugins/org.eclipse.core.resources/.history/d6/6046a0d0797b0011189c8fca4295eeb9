package com.ithelpdesk.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ithelpdesk.backend.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByEngineerId(Long engineerId);

    Optional<Feedback> findByTicketId(Long ticketId);

}