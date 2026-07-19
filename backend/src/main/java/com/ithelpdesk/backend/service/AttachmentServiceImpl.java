package com.ithelpdesk.backend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ithelpdesk.backend.dto.AttachmentResponse;
import com.ithelpdesk.backend.dto.AttachmentUploadResponse;
import com.ithelpdesk.backend.entity.Attachment;
import com.ithelpdesk.backend.entity.Ticket;
import com.ithelpdesk.backend.repository.AttachmentRepository;
import com.ithelpdesk.backend.repository.TicketRepository;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final TicketRepository ticketRepository;

    private final String uploadDir = "uploads/";

    public AttachmentServiceImpl(
            AttachmentRepository attachmentRepository,
            TicketRepository ticketRepository) {

        this.attachmentRepository = attachmentRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public AttachmentUploadResponse uploadAttachment(Long ticketId,
                                                     MultipartFile file)
            throws IOException {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        Files.createDirectories(Paths.get(uploadDir));

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        Path path = Paths.get(uploadDir, fileName);

        Files.copy(file.getInputStream(), path);

        Attachment attachment = new Attachment();

        attachment.setFileName(fileName);
        attachment.setFileType(file.getContentType());
        attachment.setFilePath(path.toString());
        attachment.setFileSize(file.getSize());
        attachment.setTicket(ticket);

        attachmentRepository.save(attachment);

        AttachmentUploadResponse response = new AttachmentUploadResponse();

        response.setMessage("Attachment uploaded successfully");
        response.setFileName(fileName);
        response.setDownloadUrl("/api/attachments/download/" + attachment.getId());

        return response;
    }

    @Override
    public List<AttachmentResponse> getAttachmentsByTicket(Long ticketId) {

        return attachmentRepository.findByTicketId(ticketId)
                .stream()
                .map(file -> {

                    AttachmentResponse response = new AttachmentResponse();

                    response.setId(file.getId());
                    response.setFileName(file.getFileName());
                    response.setFileType(file.getFileType());
                    response.setFileSize(file.getFileSize());
                    response.setUploadedAt(file.getUploadedAt());

                    return response;

                }).collect(Collectors.toList());
    }

    @Override
    public byte[] downloadAttachment(Long attachmentId)
            throws IOException {

        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new RuntimeException("Attachment not found"));

        return Files.readAllBytes(Paths.get(attachment.getFilePath()));
    }

    @Override
    public void deleteAttachment(Long attachmentId)
            throws IOException {

        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new RuntimeException("Attachment not found"));

        Files.deleteIfExists(Paths.get(attachment.getFilePath()));

        attachmentRepository.delete(attachment);
    }

}