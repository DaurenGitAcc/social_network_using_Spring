package com.absattarov.SocialNetwork.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
public class Post {
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
    @JoinColumn(name = "author",referencedColumnName = "id")
    private User author;

    @OneToMany(mappedBy = "post")
    private List<PostComment> postComments;

    public Post() {
    }

    public Post(int id, LocalDateTime createdAt, String post, int rating) {
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<PostComment> getPostComments() {
        return postComments;
    }

    public void setPostComments(List<PostComment> postComments) {
        this.postComments = postComments;
    }
}
