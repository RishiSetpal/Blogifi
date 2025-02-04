package com.example.Blogifi.enteties;

import com.example.Blogifi.configuration.AuditingConfiguration;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


// Add Lombok Dependency. via Spring boot Initializer add dependency Lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "posts") // Creates posts table if it is not created
@NamedEntityGraph(name = "Post.tags", attributeNodes = @NamedAttributeNode("tags"))
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment strategy
    private int id; // Primary key
    private String title;
    private String description; //Rename the desc column to something else, such as description, which is not a reserved keyword.

    // TODO: It should Delete from both the tables // So basically Delete References from all the Tabels
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "posts_tags", // Table Name: posts_tags
            joinColumns = {@JoinColumn(name = "post_id")}, // Column Name: post_id
            inverseJoinColumns = {@JoinColumn(name = "tag_id")} // Column Name: tag_id
    )
    private Set<Tag> tags = new HashSet<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime lastModifiedDateTime;

    //Adding Many Posts to One User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
