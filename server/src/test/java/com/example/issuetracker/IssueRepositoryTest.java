package com.example.issuetracker;

import com.example.issuetracker.entity.Issue;
import com.example.issuetracker.repository.IssueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IssueRepositoryTest {

    @Autowired
    private IssueRepository issueRepository;

    @Test
    void testSaveIssue() {
        Issue issue = new Issue();
        issue.setTitle("Repo Test");
        issue.setDescription("Repo Test Desc");
        issue.setStatus(Issue.Status.OPEN);
        issue.setPriority(Issue.Priority.HIGH);

        Issue saved = issueRepository.save(issue);

        assertNotNull(saved.getId());
        assertEquals("Repo Test", saved.getTitle());
        assertEquals(Issue.Status.OPEN, saved.getStatus());
        assertEquals(Issue.Priority.HIGH, saved.getPriority());
    }
}
