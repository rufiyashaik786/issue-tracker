package com.example.issuetracker.mapper;

import com.example.issuetracker.entity.Issue;
import com.example.issuetracker.dto.IssueResponse;

public class IssueMapper {

    public static IssueResponse toResponse(Issue issue) {
        if (issue == null) {
            return null;
        }

        IssueResponse response = new IssueResponse();

        response.setId(issue.getId());
        response.setTitle(issue.getTitle());
        response.setDescription(issue.getDescription());
        response.setStatus(issue.getStatus());
        response.setPriority(issue.getPriority());
        response.setCreatedAt(issue.getCreatedAt());
        response.setUpdatedAt(issue.getUpdatedAt());

        // No attachments field in Issue, so skip

        return response;
    }
}
