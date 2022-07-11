package com.example.userservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUser {

    @NotNull(message = "Email cannot be null")
    @Email
    private String email;

    private String name;

    @NotNull(message = "Password cannot be null")
    @Size( min = 8, message = "비밀번호는 8글자 이상이여야 합니다.")
    private String password;
}
