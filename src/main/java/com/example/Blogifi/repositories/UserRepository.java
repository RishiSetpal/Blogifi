package com.example.Blogifi.repositories;

import com.example.Blogifi.enteties.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //Use Proper Naming Convention
    Optional<User> findByUsername(String username);

}
