package com.portfolio.michael.modules.file.infrastructure.storage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.portfolio.michael.modules.file.domain.model.FileInput;
import com.portfolio.michael.modules.file.domain.port.FileStoragePort;

import jakarta.annotation.PostConstruct;

@Service
public class FileSystemStorageAdapter implements FileStoragePort {

    @Value("${app.storage.location}")
    private String storageLocation;

    private Path rootLocation;

    @PostConstruct
    public void init() {
        this.rootLocation = Paths.get(storageLocation).normalize();
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    @Override
    public String upload(FileInput file, String folder) {
        try {
            String filename = UUID.randomUUID().toString() + "_" + file.getFilename();
            Path destinationFile = this.rootLocation.resolve(Paths.get(filename))
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new RuntimeException("Cannot store file outside current directory.");
            }

            try (var inputStream = file.getContent()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            return ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/admin/files/")
                    .path(filename)
                    .toUriString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    @Override
    public void delete(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return;
        }
        try {
            // Extract filename from URL
            String filename = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            Path filePath = this.rootLocation.resolve(filename).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.err.println("Could not delete file: " + fileUrl);
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path filePath = this.rootLocation.resolve(filename).normalize().toAbsolutePath();

            if (!filePath.startsWith(this.rootLocation.toAbsolutePath())) {
                throw new RuntimeException("Cannot access file outside storage location.");
            }

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found " + filename, e);
        }
    }
}
