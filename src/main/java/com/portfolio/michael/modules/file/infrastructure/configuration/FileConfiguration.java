package com.portfolio.michael.modules.file.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.portfolio.michael.modules.file.application.usecase.GetFileUseCase;
import com.portfolio.michael.modules.file.application.usecase.UploadFileUseCase;
import com.portfolio.michael.modules.file.domain.port.FileStoragePort;

@Configuration
public class FileConfiguration {

    @Bean
    public GetFileUseCase getFileUseCase(FileStoragePort fileStoragePort) {
        return new GetFileUseCase(fileStoragePort);
    }

    @Bean
    public UploadFileUseCase uploadFileUseCase(FileStoragePort fileStoragePort) {
        return new UploadFileUseCase(fileStoragePort);
    }
}
