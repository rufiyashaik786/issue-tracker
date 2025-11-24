package com.example.issuetracker.dto;

import com.example.issuetracker.entity.Issue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class IssueRequest {

    // ---------- TITLE ----------
    @NotBlank(message = "Title is mandatory")
    @Size(min = 3, max = 150, message = "Title must be 3 to 150 characters")
    private String title;

    // ---------- DESCRIPTION ----------
    @NotBlank(message = "Description is mandatory")
    @Size(min = 10, message = "Description must be at least 10 characters")
    private String description;

    // ---------- STATUS ----------
    @NotNull(message = "Status is required")
    private Issue.Status status;

    // ---------- PRIORITY ----------
    @NotNull(message = "Priority is required")
    private Issue.Priority priority;

    // ---------- GETTERS & SETTERS ----------
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Issue.Status getStatus() { return status; }
    public void setStatus(Issue.Status status) { this.status = status; }

    public Issue.Priority getPriority() { return priority; }
    public void setPriority(Issue.Priority priority) { this.priority = priority; }
}
