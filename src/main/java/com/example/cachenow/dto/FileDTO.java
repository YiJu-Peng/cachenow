package com.example.cachenow.dto;

import com.example.cachenow.domain.File;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 时间  2023/11/10 14:23
 * 作者 Ctrlcv工程师  在线面对百度编程
 */
@Data
public class FileDTO {

    private String filename;

    private Integer uploader_id;

    private LocalDateTime uploaded_at;

    private String content_type;

    public FileDTO(File files) {
        this.filename = files.getFilename();
        this.uploader_id = files.getUploader_id();
        this.uploaded_at = files.getUploaded_at();
        this.content_type = files.getContent_type();
    }
}
