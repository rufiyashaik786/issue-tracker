package com.example.issuetracker.controller;

import com.example.issuetracker.entity.Attachment;
import com.example.issuetracker.service.AttachmentService;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/issues")
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    // ========================================================
    // 🔥  Upload SINGLE file  → POST /api/issues/{id}/attachments
    // ========================================================
    @PostMapping("/{issueId}/attachments")
    public ResponseEntity<?> uploadFile(
            @PathVariable Long issueId,
            @RequestParam("files") MultipartFile file) throws IOException {

        Attachment saved = attachmentService.uploadAttachment(issueId, file);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "File uploaded successfully");
        response.put("data", saved);

        return ResponseEntity.ok(response);
    }

    // ========================================================
    // 🔥  Upload MULTIPLE files (optional)
    // ========================================================
    @PostMapping("/{issueId}/attachments/multiple")
    public ResponseEntity<?> uploadMultipleFiles(
            @PathVariable Long issueId,
            @RequestParam("files") MultipartFile[] files) throws IOException {

        List<Attachment> uploaded = attachmentService.uploadFiles(issueId, files);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", uploaded.size() + " files uploaded successfully");
        response.put("data", uploaded);

        return ResponseEntity.ok(response);
    }

    // ========================================================
    // 🔥  Get all attachments for an issue
    // ========================================================
    @GetMapping("/{issueId}/attachments")
    public ResponseEntity<?> getAttachments(@PathVariable Long issueId) {

        List<Attachment> list = attachmentService.getAttachments(issueId);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", list);

        return ResponseEntity.ok(response);
    }

    // ========================================================
    // 🔥 Download attachment
    // ========================================================
    @GetMapping("/attachments/{attId}/download")
    public ResponseEntity<?> download(@PathVariable Long attId) {

        Resource resource = attachmentService.download(attId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    // ========================================================
    // 🔥 Delete attachment
    // ========================================================
    @DeleteMapping("/attachments/{attId}")
    public ResponseEntity<?> delete(@PathVariable Long attId) {

        attachmentService.deleteAttachment(attId);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Attachment deleted");

        return ResponseEntity.ok(response);
    }
}
