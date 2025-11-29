package com.portfolio.michael.service.admin;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.michael.entity.File;

public interface FileService {
    File saveFile(MultipartFile file, String relatedTable, Long relatedId);

    Resource loadFile(String filename);
}
