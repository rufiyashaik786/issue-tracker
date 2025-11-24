package com.example.issuetracker.dto;

import com.example.issuetracker.entity.Issue;
import java.time.LocalDateTime;
import java.util.List;

public class IssueResponse {

    private Long id;
    private String title;
    private String description;
    private Issue.Status status;
    private Issue.Priority priority;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<AttachmentDTO> attachments;   // ✅ ADD THIS

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Issue.Status getStatus() { return status; }
    public void setStatus(Issue.Status status) { this.status = status; }

    public Issue.Priority getPriority() { return priority; }
    public void setPriority(Issue.Priority priority) { this.priority = priority; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<AttachmentDTO> getAttachments() { return attachments; }
    public void setAttachments(List<AttachmentDTO> attachments) { this.attachments = attachments; }
}
