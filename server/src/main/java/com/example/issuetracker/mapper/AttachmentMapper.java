package com.example.issuetracker.mapper;

import com.example.issuetracker.dto.AttachmentDTO;
import com.example.issuetracker.entity.Attachment;

public class AttachmentMapper {

    public static AttachmentDTO toDTO(Attachment attachment) {
        if (attachment == null) return null;
        return new AttachmentDTO(
                attachment.getId(),
                attachment.getFileName(),
                attachment.getFileType(),
                attachment.getUploadedAt()
        );
    }
}
