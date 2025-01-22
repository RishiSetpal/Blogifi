package com.example.Blogifi.services;

import com.example.Blogifi.dtos.tagDto.TagRequestDto;
import com.example.Blogifi.dtos.tagDto.TagResponseDto;
import com.example.Blogifi.enteties.Tag;
import com.example.Blogifi.repositories.PostRepository;
import com.example.Blogifi.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;
    @Autowired
    PostRepository postRepository;

    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    public Tag create(Tag tag) {
        //Validate Existence of Tag
        if (tagRepository.findByName(tag.getName()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Tag with name:" + tag.getName() + " already present, you can not create duplicate tags."
            );
        }
        return tagRepository.save(tag);
    }

    public Tag update(String oldTagName, Tag tag) {
        String newTagName = tag.getName();

        //Validate if its Present in DB ot Not
        Tag existingTag = tagRepository.findByName(oldTagName).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "oldTagName:" + oldTagName + " Not Found"));

        //Validate (old & new) TagName should not be same
        if (oldTagName.equals(newTagName)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"newTagName Should not be same as oldTagName");
        }

        //Validate newTagName already Exists

        if (tagRepository.findByName(newTagName).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You cannot update oldTagName:"+oldTagName+" to newTagName:"+newTagName+" because newTagName already Exists");
        }

        existingTag.setName(tag.getName());
        return tagRepository.save(existingTag);
    }

    public void delete(String tagName){
        // check if tagName Exists in Db
        Tag tagByName = tagRepository.findByName(tagName).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag name Not Found"));
        // check References of Tags in Posts Table - if Reference Available then don't delete and show Validation Message
        // Empty then Delete else Validation
        if (!postRepository.findByTagsName(tagName).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You Can't Delete a tag that's present in post as a Reference");
        }
        tagRepository.delete(tagByName);
    }

    public Tag convertToTag(TagRequestDto tagRequestDto) {
        return new Tag(0, tagRequestDto.getName()); // TODO: Why Zero ?
    }

    public TagResponseDto convertToTagResponseDto(Tag tag) {
        return new TagResponseDto(tag.getId(), tag.getName());
    }
}
