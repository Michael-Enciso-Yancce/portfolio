package com.portfolio.michael.modules.file.application.usecase;

import org.springframework.core.io.Resource;

import com.portfolio.michael.modules.file.domain.port.FileStoragePort;

public class GetFileUseCase {

    private final FileStoragePort fileStoragePort;

    public GetFileUseCase(FileStoragePort fileStoragePort) {
        this.fileStoragePort = fileStoragePort;
    }

    public Resource execute(String filename) {
        return fileStoragePort.load(filename);
    }
}
