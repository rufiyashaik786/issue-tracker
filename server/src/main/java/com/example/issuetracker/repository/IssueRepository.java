package com.example.issuetracker.repository;

import com.example.issuetracker.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    // For listing all without pagination (can still keep it)
    List<Issue> findAllByOrderByIdAsc();
    long countByStatus(Issue.Status status);

    long countByPriority(Issue.Priority priority);

}
