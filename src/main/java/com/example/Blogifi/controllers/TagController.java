package com.example.Blogifi.controllers;

import com.example.Blogifi.dtos.tagDto.TagRequestDto;
import com.example.Blogifi.dtos.tagDto.TagResponseDto;
import com.example.Blogifi.enteties.Tag;
import com.example.Blogifi.services.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    TagService tagService;

    @Operation(summary = "Get all tags", description = "Retrieve a list of all tags.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of tags")
    })
    // Not Mandatory to use GetMapping("/withParenthesisWithSomeEndPoint") or without Endpoint GetMapping("")
    // EndPoint should be in lowerCase
    @GetMapping
    public ResponseEntity<List<TagResponseDto>> getAllTags(){
        return new ResponseEntity<List<TagResponseDto>>(
                tagService.getAll()
                        .stream()
                        .map(tag -> tagService.convertToTagResponseDto(tag))
                        .collect(Collectors.toList())
                , HttpStatus.OK
        );
    }

    @Operation(summary = "Create a new tag", description = "Create a new tag with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tag successfully created")
    })
    @PostMapping
    public ResponseEntity<TagResponseDto> createTag(@RequestBody TagRequestDto tagRequestDto) {
        Tag tag = tagService.create(tagService.convertToTag(tagRequestDto));
        TagResponseDto tagResponseDto = tagService.convertToTagResponseDto(tag);
        return new ResponseEntity<TagResponseDto>(tagResponseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Update tag by name", description = "Update a tag by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag successfully updated"),
            @ApiResponse(responseCode = "404", description = "Tag not found")
    })
    @PutMapping("/{tagName}")
    public ResponseEntity<TagResponseDto> updateTag(@PathVariable String tagName, @RequestBody TagRequestDto tagRequestDto) {
        Tag tag = tagService.update(tagName, tagService.convertToTag(tagRequestDto));
        TagResponseDto tagResponseDto = tagService.convertToTagResponseDto(tag);
        return new ResponseEntity<TagResponseDto>(tagResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "Delete tag by name", description = "Delete a tag by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tag successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Tag not found")
    })
    //<?> <T> <Void> can be used interchangeably ..
    @DeleteMapping("/{tagName}")
    public ResponseEntity<?> deleteTag(@PathVariable String tagName) {
        tagService.delete(tagName);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
