package com.portfolio.michael.modules.file.application.usecase;

import com.portfolio.michael.modules.file.domain.model.FileInput;
import com.portfolio.michael.modules.file.domain.port.FileStoragePort;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UploadFileUseCase {

    private final FileStoragePort fileStoragePort;

    public String execute(FileInput file, String folder) {
        if (folder == null || folder.isBlank()) {
            folder = "uploads";
        }
        return fileStoragePort.upload(file, folder);
    }
}
