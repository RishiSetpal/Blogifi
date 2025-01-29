package com.example.Blogifi.dtos.userDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private int id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
