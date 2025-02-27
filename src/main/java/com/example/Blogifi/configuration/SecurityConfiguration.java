package com.example.Blogifi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfiguration {

    // Here we are OverRidding By Default userDetailService method
    // this had default user as
    @Bean
    UserDetailsService userDetailsService() {
        UserDetails userDetails1 = User.builder()
                .username("rishi")
                .password(passwordEncoder().encode("rishi"))
                .build();
        UserDetails userDetails2 = User.builder()
                .username("bhavika")
                .password(passwordEncoder().encode("bhavika"))
                .build();
        UserDetails userDetails3 = User.builder()
                .username("amit")
                .password(passwordEncoder().encode("amit"))
                .build();
        return new InMemoryUserDetailsManager(userDetails1,userDetails2,userDetails3);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
