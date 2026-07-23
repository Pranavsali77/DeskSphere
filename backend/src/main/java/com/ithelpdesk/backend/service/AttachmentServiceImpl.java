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
import com.ithelpdesk.backend.entity.User;
import com.ithelpdesk.backend.repository.AttachmentRepository;
import com.ithelpdesk.backend.repository.TicketRepository;
import com.ithelpdesk.backend.repository.UserRepository;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    private final String uploadDir = "uploads/";

    public AttachmentServiceImpl(
            AttachmentRepository attachmentRepository,
            TicketRepository ticketRepository,
            UserRepository userRepository) {

        this.attachmentRepository = attachmentRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AttachmentUploadResponse uploadAttachment(
            Long ticketId,
            MultipartFile file,
            String employeeEmail)
            throws IOException {

        // ===============================
        // Validate File Type
        // ===============================
        String contentType = file.getContentType();

        if (contentType == null ||
                !(contentType.equals("application/pdf") ||
                  contentType.equals("image/png") ||
                  contentType.equals("image/jpeg") ||
                  contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))) {

            throw new RuntimeException(
                    "Only PDF, PNG, JPG, JPEG and DOCX files are allowed.");
        }

        // ===============================
        // Find Logged-in Employee
        // ===============================
        User employee = userRepository.findByEmail(employeeEmail)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        // ===============================
        // Find Ticket owned by Employee
        // ===============================
        Ticket ticket = ticketRepository
                .findByIdAndEmployee(ticketId, employee)
                .orElseThrow(() ->
                        new RuntimeException("Ticket not found"));

        // ===============================
        // Create Upload Directory
        // ===============================
        Files.createDirectories(Paths.get(uploadDir));

        // ===============================
        // Generate Unique File Name
        // ===============================
        String fileName = System.currentTimeMillis()
                + "_"
                + file.getOriginalFilename();

        Path path = Paths.get(uploadDir, fileName);

        // ===============================
        // Save File
        // ===============================
        Files.copy(file.getInputStream(), path);

        // ===============================
        // Save Attachment Details
        // ===============================
        Attachment attachment = new Attachment();

        attachment.setFileName(fileName);
        attachment.setFileType(contentType);
        attachment.setFilePath(path.toString());
        attachment.setFileSize(file.getSize());
        attachment.setTicket(ticket);

        attachmentRepository.save(attachment);

        // ===============================
        // Response
        // ===============================
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
                .orElseThrow(() ->
                        new RuntimeException("Attachment not found"));

        return Files.readAllBytes(Paths.get(attachment.getFilePath()));
    }

    @Override
    public void deleteAttachment(Long attachmentId)
            throws IOException {

        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() ->
                        new RuntimeException("Attachment not found"));

        Files.deleteIfExists(Paths.get(attachment.getFilePath()));

        attachmentRepository.delete(attachment);
    }
}