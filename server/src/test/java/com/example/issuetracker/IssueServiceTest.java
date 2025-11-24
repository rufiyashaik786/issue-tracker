package com.example.issuetracker;

import com.example.issuetracker.dto.IssueRequest;
import com.example.issuetracker.entity.Issue;
import com.example.issuetracker.entity.Issue.Priority;
import com.example.issuetracker.entity.Issue.Status;
import com.example.issuetracker.service.IssueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class IssueServiceTest {

    @Autowired
    private IssueService issueService;

    @Test
    void createAndFindIssue() {

        // DTO for creating issue
        IssueRequest request = new IssueRequest();
        request.setTitle("JUnit Test");
        request.setDescription("JUnit test description");
        request.setStatus(Status.OPEN);
        request.setPriority(Priority.MEDIUM);

        // Create Issue using DTO method
        Issue saved = issueService.createIssue(request);

        assertNotNull(saved);
        assertNotNull(saved.getId());

        Issue fetched = issueService.getIssueById(saved.getId());
        assertEquals("JUnit Test", fetched.getTitle());

        // Clean up test record
        issueService.deleteIssue(saved.getId());
    }
}
