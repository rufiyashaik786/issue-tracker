package com.example.issuetracker;

import com.example.issuetracker.entity.Issue;
import com.example.issuetracker.repository.IssueRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IssueTrackerBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(IssueTrackerBackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner printIssues(IssueRepository issueRepository) {
        return args -> {
            System.out.println("\n---- Current Issues in Database ----");

            issueRepository.findAll().forEach(issue ->
                System.out.println(
                    "ID: " + issue.getId() +
                    ", Title: " + issue.getTitle() +
                    ", Status: " + issue.getStatus() +
                    ", Priority: " + issue.getPriority()
                )
            );

            System.out.println("-----------------------------------\n");
        };
    }
}
