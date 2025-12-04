package com.portfolio.michael.modules.file.domain.model;

import java.io.InputStream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInput {
    private String filename;
    private String contentType;
    private InputStream content;
    private long size;
}
