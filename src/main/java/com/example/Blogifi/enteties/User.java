package com.example.Blogifi.enteties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//For Database creation
@Entity

//For Enabling Auditing
@EntityListeners(AuditingEntityListener.class)

//Lombok
@Data //Creates Getters and Setters
@NoArgsConstructor //Creates No Args Constructor
@AllArgsConstructor //Creates All Args Constructor
//To Create A custom you can Create a Custom Constructor with only Some Parameters, or with Different Structure

public class User {
    @Id //For Making id as Primary Key for MySQL
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String Email;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

        @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Post> posts = new ArrayList<>();

}
