package com.example.issuetracker.service;

import com.example.issuetracker.entity.Attachment;
import com.example.issuetracker.entity.Issue;
import com.example.issuetracker.exception.FileStorageException;
import com.example.issuetracker.exception.InvalidFileTypeException;
import com.example.issuetracker.exception.ResourceNotFoundException;
import com.example.issuetracker.repository.AttachmentRepository;
import com.example.issuetracker.repository.IssueRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final IssueRepository issueRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public AttachmentService(AttachmentRepository attachmentRepository,
                             IssueRepository issueRepository) {
        this.attachmentRepository = attachmentRepository;
        this.issueRepository = issueRepository;
    }

    private static final List<String> ALLOWED_TYPES = List.of(
            "image/png",
            "image/jpeg",
            "image/jpg",
            "application/pdf"
    );

    private static final long MAX_SIZE = 10 * 1024 * 1024;

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileStorageException("File cannot be empty");
        }

        if (file.getSize() > MAX_SIZE) {
            throw new FileStorageException("File exceeds 10MB");
        }

        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new InvalidFileTypeException("Invalid file type: " + file.getContentType());
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            throw new FileStorageException("Invalid file name: " + fileName);
        }
    }

    public Attachment uploadAttachment(Long issueId, MultipartFile file) throws IOException {

        validateFile(file);

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found: " + issueId));

        Files.createDirectories(Paths.get(uploadDir));

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path target = Paths.get(uploadDir).resolve(fileName);

        Files.copy(file.getInputStream(), target);

        Attachment att = new Attachment();
        att.setFileName(file.getOriginalFilename());
        att.setFileType(file.getContentType());
        att.setFilePath(target.toString());
        att.setUploadedAt(LocalDateTime.now());
        att.setIssue(issue);

        attachmentRepository.save(att);

        issue.setUpdatedAt(LocalDateTime.now());
        issueRepository.save(issue);

        return att;
    }

    public List<Attachment> uploadFiles(Long issueId, MultipartFile[] files) throws IOException {
        List<Attachment> uploaded = new ArrayList<>();
        for (MultipartFile file : files) uploaded.add(uploadAttachment(issueId, file));
        return uploaded;
    }

    public List<Attachment> getAttachments(Long issueId) {
        return attachmentRepository.findByIssueId(issueId);
    }

    public Resource download(Long attId) {
        Attachment att = attachmentRepository.findById(attId)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found"));

        return new FileSystemResource(att.getFilePath());
    }

    public void deleteAttachment(Long attId) {
        Attachment att = attachmentRepository.findById(attId)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found"));

        File file = new File(att.getFilePath());
        if (file.exists()) file.delete();

        attachmentRepository.delete(att);
    }
}
