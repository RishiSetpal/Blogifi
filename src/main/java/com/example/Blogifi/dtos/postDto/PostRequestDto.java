package com.example.Blogifi.dtos.postDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostRequestDto {
    private String title;
    private String Description;
}
