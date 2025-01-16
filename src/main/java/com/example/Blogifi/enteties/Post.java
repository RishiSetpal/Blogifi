package com.example.Blogifi.enteties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.LinkedHashSet;


// Add Lombok Dependency. via Spring boot Initializer add dependency Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "posts")
public class Post {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment strategy
    private int id; // Primary key
    private String title;
    private String description; //Rename the desc column to something else, such as description, which is not a reserved keyword.

}
