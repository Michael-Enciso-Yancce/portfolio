package com.portfolio.michael.mapper;

import org.mapstruct.Mapper;

import com.portfolio.michael.dto.visitor.request.UserRequest;
import com.portfolio.michael.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequest userRequest);
}
