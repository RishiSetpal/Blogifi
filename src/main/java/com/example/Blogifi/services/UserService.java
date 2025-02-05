package com.example.Blogifi.services;

import com.example.Blogifi.dtos.userDto.UserPartialRequestDto;
import com.example.Blogifi.dtos.userDto.UserRequestDto;
import com.example.Blogifi.dtos.userDto.UserResponseDto;
import com.example.Blogifi.enteties.User;
import com.example.Blogifi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User create(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with username:" + user.getUsername() + "is already exists");
        }
        return userRepository.save(user);
    }

    public Page<User> getAll(int page, int size, String sortDirection, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return userRepository.findAll(pageable);
    }

    public User getById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " Not Found"));
    }

    public User update(int id, User user) {
        User existingUser = getById(id);
        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }
        if (user.getPassword() != null) {
            existingUser.setPassword(user.getPassword());
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        return userRepository.save(existingUser);
    }

    public void delete(int id) {
        getById(id);
        userRepository.deleteById(id);
    }

    public User convertToUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setPassword(userRequestDto.getPassword());
        user.setEmail(userRequestDto.getEmail());
        return user;
    }

    public User convertToUser(UserPartialRequestDto userPartialRequestDto){
        User user = new User();
        user.setUsername(userPartialRequestDto.getUsername());
        user.setPassword(userPartialRequestDto.getPassword());
        user.setEmail(userPartialRequestDto.getEmail());
        return user;
    }

    public UserResponseDto convertToUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setPassword(user.getPassword());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setCreatedDate(user.getCreatedDate());
        userResponseDto.setLastModifiedDate(user.getLastModifiedDate());
        return userResponseDto;
    }
}