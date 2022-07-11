package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.repository.UserEntity;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.utils.ModelMapperUtil;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository repository;
    private BCryptPasswordEncoder pwdEncoder;

    public  UserServiceImpl( UserRepository repository,  BCryptPasswordEncoder pwdEncoder) {
        this.pwdEncoder =pwdEncoder;
        this.repository = repository;
    }

    @Override
    public UserDto createUser(UserDto user) {
        // userDto > userEntity 변환 > userRepository save 호출

        user.setUserId( UUID.randomUUID().toString() );
        user.setEncryptedPassword( pwdEncoder.encode( user.getPassword()) );
        UserEntity userEntity = ModelMapperUtil.map( user, UserEntity.class );

        repository.save( userEntity );

        return user;
    }

    @Override
    public List<UserDto> findAll() {

        return ModelMapperUtil.mapAll( repository.findAll(), UserDto.class);
    }

    @Override
    public UserDto findByUserId( String userId ) {

        return ModelMapperUtil.map( repository.findAllByUserId( userId ), UserDto.class );
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity userEntity = repository.findByEmail( username );

        if(userEntity == null) {
            throw new UsernameNotFoundException( username );
        }

        return new User( userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = repository.findByEmail( email );

        return ModelMapperUtil.map(userEntity, UserDto.class );
    }
}
