package com.absattarov.SocialNetwork.DTO;

import com.absattarov.SocialNetwork.models.Group;
import com.absattarov.SocialNetwork.models.GroupPostComment;
import com.absattarov.SocialNetwork.models.PostComment;
import com.absattarov.SocialNetwork.models.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PostDTO {
    private int id;
    private LocalDateTime createdAt;
    private String post;
    private Set<Integer> groupContactsId;
    private List<PostComment> postComments;
    private List<GroupPostComment> groupPostComments;
    private int rating;
    private User author;
    private Group group;

    {         // fixing bug
        author = new User();
        author.setId(-1);
        author.setName("TestName");
        author.setSurname("UserSurname");
        author.setAvatar("Avatar");
        group = new Group();
        group.setName("TestName");
        group.setAvatar("Avatar");

        groupContactsId=new HashSet<>();

        postComments = new ArrayList<>();
        groupPostComments = new ArrayList<>();
    }
    public String getJsonn() throws JsonProcessingException {
       return "";
    }

    public Set<Integer> getGroupContactsId() {
        return groupContactsId;
    }

    public List<GroupPostComment> getGroupPostComments() {
        return groupPostComments;
    }

    public void setGroupPostComments(List<GroupPostComment> groupPostComments) {
        this.groupPostComments = groupPostComments;
    }

    public void setGroupContactsId(Set<Integer> groupContactsId) {
        this.groupContactsId = groupContactsId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<PostComment> getPostComments() {
        return postComments;
    }

    public void setPostComments(List<PostComment> postComments) {
        this.postComments = postComments;
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
