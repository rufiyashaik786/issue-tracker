package com.example.issuetracker.controller;

import com.example.issuetracker.dto.IssueRequest;
import com.example.issuetracker.entity.Issue;
import com.example.issuetracker.service.IssueService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    // ----------------------------------------------------
    // CREATE ISSUE
    // ----------------------------------------------------
    @PostMapping
    public ResponseEntity<?> createIssue(@Valid @RequestBody IssueRequest request) {

        Issue created = issueService.createIssue(request);

        return ResponseEntity.ok(created);
    }

    // ----------------------------------------------------
    // GET ISSUE BY ID
    // ----------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<?> getIssue(@PathVariable Long id) {

        Issue issue = issueService.getIssueById(id);

        return ResponseEntity.ok(issue);
    }

    // ----------------------------------------------------
    // LIST WITH PAGINATION + FILTERS + SEARCH
    // ----------------------------------------------------
    @GetMapping
    public ResponseEntity<?> getIssues(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) Issue.Status status,
            @RequestParam(required = false) Issue.Priority priority,
            @RequestParam(required = false) String search
    ) {

        return ResponseEntity.ok(
                issueService.getIssues(page, size, sortBy, sortDir, status, priority, search)
        );
    }

    // ----------------------------------------------------
    // UPDATE ISSUE
    // ----------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<?> updateIssue(
            @PathVariable Long id,
            @Valid @RequestBody IssueRequest request
    ) {
        Issue updated = issueService.updateIssue(id, request);
        return ResponseEntity.ok(updated);
    }

    // ----------------------------------------------------
    // DELETE ISSUE
    // ----------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        issueService.deleteIssue(id);

        return ResponseEntity.ok("Issue deleted");
    }

    // ----------------------------------------------------
    // DASHBOARD SUMMARY
    // ----------------------------------------------------
    @GetMapping("/dashboard/summary")
    public ResponseEntity<?> getDashboardSummary() {

        return ResponseEntity.ok(
                issueService.getDashboardSummary()
        );
    }
}
