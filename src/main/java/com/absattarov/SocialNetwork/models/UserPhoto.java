package com.absattarov.SocialNetwork.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user_photo")
public class UserPhoto {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "photo_path")
    private String photoPath;
    @Column(name = "rating")
    private int rating;
    @ManyToOne
    @JoinColumn(name = "user_of",referencedColumnName = "id")
    private User user;
    @OneToMany(mappedBy = "userPhoto")
    private List<UserPhotoComment> userPhotoComments;

    public UserPhoto() {
    }

    public UserPhoto(int id, LocalDateTime createdAt, String photoPath, int rating) {
        this.id = id;
        this.createdAt = createdAt;
        this.photoPath = photoPath;
        this.rating = rating;
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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<UserPhotoComment> getUserPhotoComments() {
        return userPhotoComments;
    }

    public void setUserPhotoComments(List<UserPhotoComment> userPhotoComments) {
        this.userPhotoComments = userPhotoComments;
    }
}
