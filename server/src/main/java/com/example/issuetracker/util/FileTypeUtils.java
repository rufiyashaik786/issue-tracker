package com.example.issuetracker.util;

import com.example.issuetracker.exception.InvalidFileTypeException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public final class FileTypeUtils {

    private static final List<String> ALLOWED_TYPES = List.of(
            "image/jpeg",
            "image/png",
            "application/pdf"
    );

    private FileTypeUtils() {}

    public static void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InvalidFileTypeException("No file provided");
        }
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            throw new InvalidFileTypeException("Only JPG, PNG, and PDF files are allowed");
        }
    }
}
