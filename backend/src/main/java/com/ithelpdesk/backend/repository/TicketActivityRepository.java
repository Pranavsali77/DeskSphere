package com.ithelpdesk.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ithelpdesk.backend.entity.TicketActivity;

@Repository
public interface TicketActivityRepository extends JpaRepository<TicketActivity, Long> {

    List<TicketActivity> findByTicketIdOrderByPerformedAtAsc(Long ticketId);

}