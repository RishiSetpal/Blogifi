package com.example.Blogifi.controllers;

import com.example.Blogifi.dtos.tagDto.TagRequestDto;
import com.example.Blogifi.dtos.tagDto.TagResponseDto;
import com.example.Blogifi.enteties.Tag;
import com.example.Blogifi.services.TagService;
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

    @PostMapping
    public ResponseEntity<TagResponseDto> createTag(@RequestBody TagRequestDto tagRequestDto) {
        Tag tag = tagService.create(tagService.convertToTag(tagRequestDto));
        TagResponseDto tagResponseDto = tagService.convertToTagResponseDto(tag);
        return new ResponseEntity<TagResponseDto>(tagResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{tagName}")
    public ResponseEntity<TagResponseDto> updateTag(@PathVariable String tagName, @RequestBody TagRequestDto tagRequestDto) {
        Tag tag = tagService.update(tagName, tagService.convertToTag(tagRequestDto));
        TagResponseDto tagResponseDto = tagService.convertToTagResponseDto(tag);
        return new ResponseEntity<TagResponseDto>(tagResponseDto, HttpStatus.OK);
    }

    //<?> <T> <Void> can be used interchangeably ..
    @DeleteMapping("/{tagName}")
    public ResponseEntity<?> deleteTag(@PathVariable String tagName) {
        tagService.delete(tagName);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
