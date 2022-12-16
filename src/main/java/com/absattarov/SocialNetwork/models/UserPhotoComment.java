package com.absattarov.SocialNetwork.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_photo_comment")
public class UserPhotoComment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "comment")
    private String comment;
    @ManyToOne
    @JoinColumn(name = "user_photo",referencedColumnName = "id")
    private UserPhoto userPhoto;
    @ManyToOne
    @JoinColumn(name = "author",referencedColumnName = "id")
    private User author;

    public UserPhotoComment() {
    }

    public UserPhotoComment(int id, LocalDateTime createdAt, String comment) {
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

    public UserPhoto getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(UserPhoto userPhoto) {
        this.userPhoto = userPhoto;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
