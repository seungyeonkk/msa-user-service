package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.utils.ModelMapperUtil;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class UserController {

    private Environment env;
    private UserService userService;

    @Autowired
    public UserController (Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @GetMapping("/")
    String hello() {
        return "hello";
    }

    @GetMapping("/health-check")
    public String status() {
        return String.format("user-service connected, port(server.port): "
                + env.getProperty("server.port") + ", welcome : "
                + env.getProperty("greeting.name"));
    }

    @GetMapping("/users/health-check")
    public String status2() {
        return new StringBuilder().append( "Connected Success" )
                .append( ", service name: " )
                .append( env.getProperty("spring.application.name") )
                .append( ", server port: " )
                .append( env.getProperty("server.port") )
                .toString();
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> readUsers() {
        return ResponseEntity
                .status( HttpStatus.OK )
                .body( ModelMapperUtil.mapAll( userService.findAll(), ResponseUser.class ));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> readUser(@PathVariable String userId) {
        return ResponseEntity
                .status( HttpStatus.OK )
                .body( ModelMapperUtil.map( userService.findByUserId( userId ), ResponseUser.class ));
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> modifyUser(@PathVariable String userId) {
        return ResponseEntity
                .status( HttpStatus.OK )
                .body( ModelMapperUtil.map( userService.findByUserId( userId ), ResponseUser.class ));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        return ResponseEntity
                .status( HttpStatus.OK )
                .body( "삭제 성공" );
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser (@Valid @RequestBody RequestUser user ) {

        UserDto userDto = ModelMapperUtil.map( user, UserDto.class );
        userDto = userService.createUser( userDto );
        ResponseUser responseUser = ModelMapperUtil.map( userDto, ResponseUser.class );

        return ResponseEntity.status( HttpStatus.CREATED ).body( responseUser );
    }
}
