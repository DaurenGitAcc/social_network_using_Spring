package com.absattarov.SocialNetwork.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "group_post")
public class GroupPost {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "post")
    private String post;
    @Column(name = "rating")
    private int rating;
    @ManyToOne
    @JoinColumn(name = "group",referencedColumnName = "id")
    private Group group;
    @OneToMany(mappedBy = "groupPost")
    private List<GroupPostComment> groupPostComments;

    public GroupPost() {
    }

    public GroupPost(int id, LocalDateTime createdAt, String post, int rating) {
        this.id = id;
        this.createdAt = createdAt;
        this.post = post;
        this.rating = rating;
    }

    public String getJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
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
