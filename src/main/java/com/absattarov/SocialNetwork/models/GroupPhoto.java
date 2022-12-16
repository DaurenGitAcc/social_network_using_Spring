package com.absattarov.SocialNetwork.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "group_photo_comment")
public class GroupPhoto {
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
    @JoinColumn(name = "group",referencedColumnName = "id")
    private Group group;

    @OneToMany(mappedBy = "groupPhoto")
    private List<GroupPhotoComment> groupPhotoComments;

    public GroupPhoto() {
    }

    public GroupPhoto(int id, LocalDateTime createdAt, String photoPath, int rating) {
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<GroupPhotoComment> getGroupPhotoComments() {
        return groupPhotoComments;
    }

    public void setGroupPhotoComments(List<GroupPhotoComment> groupPhotoComments) {
        this.groupPhotoComments = groupPhotoComments;
    }
}
