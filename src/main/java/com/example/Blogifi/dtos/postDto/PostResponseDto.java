package com.example.Blogifi.dtos.postDto;

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
    private LocalDateTime createdDateTime;
    private LocalDateTime lastModifiedDateTime;
}
