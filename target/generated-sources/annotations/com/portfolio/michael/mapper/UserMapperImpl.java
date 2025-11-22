package com.portfolio.michael.mapper;

import com.portfolio.michael.dto.visitor.request.UserRequest;
import com.portfolio.michael.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-22T18:36:44-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.name( userRequest.getName() );
        user.lastName( userRequest.getLastName() );
        user.password( userRequest.getPassword() );
        user.email( userRequest.getEmail() );

        return user.build();
    }
}
