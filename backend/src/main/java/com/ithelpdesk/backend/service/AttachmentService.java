package com.ithelpdesk.backend.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ithelpdesk.backend.dto.AttachmentResponse;
import com.ithelpdesk.backend.dto.AttachmentUploadResponse;

public interface AttachmentService {

    AttachmentUploadResponse uploadAttachment(Long ticketId, MultipartFile file) throws IOException;

    List<AttachmentResponse> getAttachmentsByTicket(Long ticketId);

    byte[] downloadAttachment(Long attachmentId) throws IOException;

    void deleteAttachment(Long attachmentId) throws IOException;

}