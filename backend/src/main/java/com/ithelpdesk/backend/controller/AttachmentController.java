package com.ithelpdesk.backend.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ithelpdesk.backend.dto.AttachmentResponse;
import com.ithelpdesk.backend.dto.AttachmentUploadResponse;
import com.ithelpdesk.backend.service.AttachmentService;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/upload/{ticketId}")
    public ResponseEntity<AttachmentUploadResponse> uploadAttachment(
        @PathVariable Long ticketId,
        @RequestParam("file") MultipartFile file,
        @RequestParam String email)
        throws IOException {

        return ResponseEntity.ok(
            attachmentService.uploadAttachment(
                    ticketId,
                    file,
                    email));
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<AttachmentResponse>> getAttachments(
            @PathVariable Long ticketId) {

        return ResponseEntity.ok(
                attachmentService.getAttachmentsByTicket(ticketId));
    }

    @GetMapping("/download/{attachmentId}")
    public ResponseEntity<byte[]> downloadAttachment(
            @PathVariable Long attachmentId)
            throws IOException {

        byte[] file = attachmentService.downloadAttachment(attachmentId);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .filename("attachment")
                        .build());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

    @DeleteMapping("/{attachmentId}")
    public ResponseEntity<String> deleteAttachment(
            @PathVariable Long attachmentId)
            throws IOException {

        attachmentService.deleteAttachment(attachmentId);

        return ResponseEntity.ok("Attachment deleted successfully");
    }
}