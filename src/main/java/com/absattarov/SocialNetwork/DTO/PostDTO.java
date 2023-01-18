package com.absattarov.SocialNetwork.DTO;

import com.absattarov.SocialNetwork.models.Group;
import com.absattarov.SocialNetwork.models.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;

public class PostDTO {
    private LocalDateTime createdAt;

    private String post;

    private int rating;
    private User author;
    private Group group;

    {         // fixing bug
        author = new User();
        author.setName("TestName");
        author.setSurname("UserSurname");
        author.setAvatar("Avatar");
        group = new Group();
        group.setName("TestName");
        group.setAvatar("Avatar");
    }

    public String getJsonn() throws JsonProcessingException {
       return "";
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
