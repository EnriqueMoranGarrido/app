package com.aevw.app.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLogInRequest {

    @NotNull
    @NotBlank(message = "Email is mandatory")
    @Email(message = "email should be a valid email format")
    private String email;

    @NotNull
    @NotBlank(message = "Password is mandatory")
    @Size(min = 2, message = "Password should have at least 2 characters")
    private String password;

}
