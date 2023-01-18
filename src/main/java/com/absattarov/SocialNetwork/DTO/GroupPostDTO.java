package com.absattarov.SocialNetwork.DTO;

import com.absattarov.SocialNetwork.models.Group;
import com.absattarov.SocialNetwork.models.GroupPostComment;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class GroupPostDTO extends PostDTO{

    private int id;

    private LocalDateTime createdAt;

    private String post;

    private int rating;

    private Group group;

    private List<GroupPostComment> groupPostComments;

    public GroupPostDTO() {
    }

    public GroupPostDTO(LocalDateTime createdAt, String post, int rating, Group group, List<GroupPostComment> groupPostComments) {
        this.createdAt = createdAt;
        this.post = post;
        this.rating = rating;
        this.group = group;
        this.groupPostComments = groupPostComments;
    }
    @Override
    public String getJsonn() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper.writeValueAsString(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<GroupPostComment> getGroupPostComments() {
        return groupPostComments;
    }

    public void setGroupPostComments(List<GroupPostComment> groupPostComments) {
        this.groupPostComments = groupPostComments;
    }
}
