package com.portfolio.michael.modules.file.domain.port;

import org.springframework.core.io.Resource;

import com.portfolio.michael.modules.file.domain.model.FileInput;

public interface FileStoragePort {
    String upload(FileInput file, String folder);

    void delete(String fileUrl);

    Resource load(String filename);
}
