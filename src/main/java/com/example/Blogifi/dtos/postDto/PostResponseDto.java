package com.example.Blogifi.dtos.postDto;

import com.example.Blogifi.dtos.userDto.UserAuthorResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.sql.results.spi.LoadContexts;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private int id;
    private String title;
    private String Description;
    private Set<String> tags;
    private UserAuthorResponseDto author;
    private String imageUrl;
    private LocalDateTime createdDateTime;
    private LocalDateTime lastModifiedDateTime;
}
