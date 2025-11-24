package com.example.issuetracker.service;

import com.example.issuetracker.dto.IssueRequest;
import com.example.issuetracker.entity.Issue;
import com.example.issuetracker.exception.ResourceNotFoundException;
import com.example.issuetracker.repository.IssueRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class IssueService {

    private final IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    // ----------------------------------------------------
    // CREATE ISSUE
    // ----------------------------------------------------
    public Issue createIssue(IssueRequest req) {
        Issue issue = new Issue();
        issue.setTitle(req.getTitle());
        issue.setDescription(req.getDescription());
        issue.setStatus(req.getStatus());
        issue.setPriority(req.getPriority());
        return issueRepository.save(issue);
    }

    // ----------------------------------------------------
    // GET ONE
    // ----------------------------------------------------
    public Issue getIssueById(Long id) {
        return issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id " + id));
    }

    // ----------------------------------------------------
    // UPDATE ISSUE
    // ----------------------------------------------------
    public Issue updateIssue(Long id, IssueRequest req) {

        Issue existing = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        existing.setTitle(req.getTitle());
        existing.setDescription(req.getDescription());
        existing.setStatus(req.getStatus());
        existing.setPriority(req.getPriority());

        return issueRepository.save(existing);
    }

    // ----------------------------------------------------
    // DELETE ISSUE
    // ----------------------------------------------------
    public void deleteIssue(Long id) {
        Issue issue = getIssueById(id);
        issueRepository.delete(issue);
    }

    // ----------------------------------------------------
    // LIST WITH FILTERS + SEARCH + SORT + PAGINATION
    // ----------------------------------------------------
    public Map<String, Object> getIssues(
            int page, int size, String sortBy, String sortDir,
            Issue.Status status, Issue.Priority priority, String search
    ) {

        List<Issue> issues = issueRepository.findAll();

        // STATUS FILTER
        if (status != null) {
            issues = issues.stream()
                    .filter(i -> i.getStatus() == status)
                    .collect(Collectors.toList());
        }

        // PRIORITY FILTER
        if (priority != null) {
            issues = issues.stream()
                    .filter(i -> i.getPriority() == priority)
                    .collect(Collectors.toList());
        }

        // SEARCH FILTER
        if (search != null && !search.isEmpty()) {
            String s = search.toLowerCase();
            issues = issues.stream()
                    .filter(i -> i.getTitle().toLowerCase().contains(s))
                    .collect(Collectors.toList());
        }

        // SORTING
     // SORTING
        Comparator<Issue> comparator = (a, b) -> 0;

        switch (sortBy) {
            case "createdAt" -> {
                comparator = Comparator.comparing(Issue::getCreatedAt);
            }
            case "priority" -> {
                comparator = Comparator.comparing(i -> i.getPriority().ordinal());
            }
            case "status" -> {
                comparator = Comparator.comparing(i -> i.getStatus().ordinal());
            }
            default -> {
                comparator = Comparator.comparing(Issue::getCreatedAt);
            }
        }

        if (sortDir.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }

        issues = issues.stream().sorted(comparator).toList();

        // PAGINATION
        int start = page * size;
        int end = Math.min(start + size, issues.size());
        List<Issue> paginated = issues.subList(start, end);

        Map<String, Object> res = new HashMap<>();
        res.put("data", paginated);
        res.put("currentPage", page);
        res.put("totalItems", issues.size());
        res.put("totalPages", (int) Math.ceil((double) issues.size() / size));

        return res;
    }

    private Comparable<?> getField(Issue i, String field) {
        return switch (field) {
            case "createdAt" -> i.getCreatedAt();
            case "priority" -> i.getPriority();
            case "status" -> i.getStatus();
            default -> i.getCreatedAt();
        };
    }

    // ----------------------------------------------------
    // DASHBOARD SUMMARY
    // ----------------------------------------------------
    public Map<String, Object> getDashboardSummary() {

        long total = issueRepository.count();
        long open = issueRepository.countByStatus(Issue.Status.OPEN);
        long inProgress = issueRepository.countByStatus(Issue.Status.IN_PROGRESS);
        long resolved = issueRepository.countByStatus(Issue.Status.RESOLVED);
        long closed = issueRepository.countByStatus(Issue.Status.CLOSED);

        long highPriority = issueRepository.countByPriority(Issue.Priority.HIGH);
        long mediumPriority = issueRepository.countByPriority(Issue.Priority.MEDIUM);
        long lowPriority = issueRepository.countByPriority(Issue.Priority.LOW);

        Map<String, Object> res = new HashMap<>();
        res.put("totalIssues", total);
        res.put("openIssues", open);
        res.put("inProgressIssues", inProgress);
        res.put("resolvedIssues", resolved);
        res.put("closedIssues", closed);
        res.put("highPriority", highPriority);
        res.put("mediumPriority", mediumPriority);
        res.put("lowPriority", lowPriority);

        return res;
    }
}
