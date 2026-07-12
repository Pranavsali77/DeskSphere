package com.ithelpdesk.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ithelpdesk.backend.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    List<Attachment> findByTicketId(Long ticketId);

}