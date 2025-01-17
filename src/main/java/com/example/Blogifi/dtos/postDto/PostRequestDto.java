package com.example.Blogifi.dtos.postDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class PostRequestDto {
    private String title;
    private String Description;
    private Set<String> tags;
}
