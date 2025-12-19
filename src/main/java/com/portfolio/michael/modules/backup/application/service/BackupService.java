package com.portfolio.michael.modules.backup.application.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;

public interface BackupService {
    void exportBackup(HttpServletResponse response) throws IOException;

    void importBackup(MultipartFile file) throws IOException;
}
