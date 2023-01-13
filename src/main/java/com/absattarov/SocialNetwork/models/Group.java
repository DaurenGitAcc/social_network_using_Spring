package com.absattarov.SocialNetwork.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "avatar")
    private String avatar;

    @ManyToMany
    @JoinTable(
            name = "group_contact",
            joinColumns = @JoinColumn(name = "groupp"),
            inverseJoinColumns = @JoinColumn(name = "userr"))
    @JsonIgnore
    private List<User> contacts;

    @ManyToMany
    @JoinTable(
            name = "subscription_group",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber"))
    @JsonIgnore
    private List<User> members;

    @OneToMany(mappedBy = "group")
    @JsonIgnore
    private List<GroupPhoto> groupPhotos;
    @OneToMany(mappedBy = "group")
    @JsonIgnore
    private List<GroupPost> groupPosts;

    public Group() {
    }

    public Group(int id, LocalDateTime createdAt, String name, String description, String avatar) {
        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.description = description;
        this.avatar = avatar;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<User> getContacts() {
        return contacts;
    }

    public void setContacts(List<User> contacts) {
        this.contacts = contacts;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<GroupPhoto> getGroupPhotos() {
        return groupPhotos;
    }

    public void setGroupPhotos(List<GroupPhoto> groupPhotos) {
        this.groupPhotos = groupPhotos;
    }

    public List<GroupPost> getGroupPosts() {
        return groupPosts;
    }

    public void setGroupPosts(List<GroupPost> groupPosts) {
        this.groupPosts = groupPosts;
    }



    @Override
    public int hashCode() {
        return id;
    }
}
