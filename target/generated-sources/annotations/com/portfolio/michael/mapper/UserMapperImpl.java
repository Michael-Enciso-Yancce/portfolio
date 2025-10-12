package com.portfolio.michael.mapper;

import com.portfolio.michael.dto.visitor.request.UserRequest;
import com.portfolio.michael.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-11T15:54:44-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251001-1143, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( userRequest.getEmail() );
        user.lastName( userRequest.getLastName() );
        user.name( userRequest.getName() );
        user.password( userRequest.getPassword() );

        return user.build();
    }
}
