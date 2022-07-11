package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto createUser( UserDto user );

    List<UserDto> findAll();

    UserDto findByUserId( String userId );

    UserDetails loadUserByUsername(String username);

    UserDto getUserDetailsByEmail( String email );
}
