package com.example.Blogifi.enteties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.LinkedHashSet;


// Add Lombok Dependency. via Spring boot Initializer add dependency Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    private int id;
    private String title;
    private String desc;

}
