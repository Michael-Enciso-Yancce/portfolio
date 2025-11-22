package com.portfolio.michael.mapper;

import org.mapstruct.Mapper;

import com.portfolio.michael.dto.admin.response.FileResponse;
import com.portfolio.michael.entity.File;

@Mapper(componentModel = "spring")
public interface FileMapper {
    FileResponse toResponse(File file);
}
