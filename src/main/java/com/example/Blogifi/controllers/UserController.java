package com.example.Blogifi.controllers;

import com.example.Blogifi.dtos.userDto.UserRequestDto;
import com.example.Blogifi.dtos.userDto.UserResponseDto;
import com.example.Blogifi.enteties.User;
import com.example.Blogifi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Page<UserResponseDto> allUsers = userService.getAll(page, size, sortDirection, sortBy)
                .map(user-> userService.convertToUserResponseDto(user));
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto){
        User user = userService.convertToUser(userRequestDto);
        User createdUser  = userService.create(user);
        UserResponseDto userResponseDto = userService.convertToUserResponseDto(createdUser);
        return ResponseEntity.status(201).body(userResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable int id){
        User user = userService.getById(id);
        UserResponseDto userResponseDto = userService.convertToUserResponseDto(user);
        return ResponseEntity.ok(userResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable int id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUserById(@PathVariable int id, @RequestBody UserRequestDto userRequestDto){

        User user = userService.convertToUser(userRequestDto);
        User updatedUser  = userService.update(id, user);
        UserResponseDto userResponseDto = userService.convertToUserResponseDto(updatedUser);
        return ResponseEntity.ok(userResponseDto);
    }

}
