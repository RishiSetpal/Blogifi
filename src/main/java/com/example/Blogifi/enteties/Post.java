package com.example.Blogifi.enteties;

import java.util.LinkedHashSet;

public class Post {

    private int id;
    private String title;
    private String desc;
    private LinkedHashSet<String> tags;

    //Default Constructor
    public Post() {
    }

    //Parameterized Constructor
    public Post(int id, String title, String desc, LinkedHashSet<String> tags) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.tags = tags;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LinkedHashSet<String> getTags() {
        return tags;
    }

    public void setTags(LinkedHashSet<String> tags) {
        this.tags = tags;
    }

    //Override toString method to Object Parameters instead of Object ID
    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", tags=" + tags +
                '}';
    }
}
