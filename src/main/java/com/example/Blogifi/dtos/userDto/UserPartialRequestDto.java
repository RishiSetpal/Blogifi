package com.example.Blogifi.dtos.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPartialRequestDto {

    @Size(min = 3, max = 20, message = "username must be between 3 and 20 characters")
    private String username;

    @Size(min = 8, max = 15, message = "password must be between 8 and 15 characters")
    private String password;

    @Email(message = "Email should be Valid")
    private String email;
}
