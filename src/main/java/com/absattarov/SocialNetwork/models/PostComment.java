package com.absattarov.SocialNetwork.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "post_comment")
public class PostComment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "comment")
    private String comment;
    @ManyToOne
    @JoinColumn(name = "post",referencedColumnName = "id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "author",referencedColumnName = "id")
    private User author;

    public PostComment() {
    }

    public PostComment(int id, LocalDateTime createdAt, String comment) {
        this.id = id;
        this.createdAt = createdAt;
        this.comment = comment;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
