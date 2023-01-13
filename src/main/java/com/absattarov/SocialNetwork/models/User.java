package com.absattarov.SocialNetwork.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email")
    @JsonIgnore
    private String email;
    @Column(name = "phone_number")
    @JsonIgnore
    private String phoneNumber;
    @Column(name = "password")
    @JsonIgnore
    private String password;
    @Column(name = "avatar")
    private String avatar;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_date")
    @JsonIgnore
    private LocalDate birthDate;
    @Column(name = "location")
    @JsonIgnore
    private String location;
    @Column(name = "job")
    @JsonIgnore
    private String job;
    @Column(name = "school")
    @JsonIgnore
    private String school;
    @Column(name = "university")
    @JsonIgnore
    private String university;
    @Column(name = "status")
    @JsonIgnore
    private String status;
    @Column(name = "role")
    @JsonIgnore
    private String role;
///

    @ManyToMany
    @JoinTable(
        name = "friends",
        joinColumns = @JoinColumn(name = "userr"),
        inverseJoinColumns = @JoinColumn(name = "friend"))
    @JsonIgnore
    private List<User> friends;
    @ManyToMany
    @JoinTable(
            name = "subscriber",
            joinColumns = @JoinColumn(name = "userr"),
            inverseJoinColumns = @JoinColumn(name = "subscriber"))
    @JsonIgnore
    private List<User> subscribers;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserPhoto> userPhotos;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<Post> posts;

    ///////////
    @ManyToMany(mappedBy = "members")
    @JsonIgnore
    private List<Group> subscriptionGroup;

    @ManyToMany(mappedBy = "contacts")
    @JsonIgnore
    private List<Group> groupsModer;

    public User() {
    }

    public User(int id, String name, String surname, String email, String phoneNumber, String password, String avatar, LocalDate birthDate, String location, String job, String school, String university, String status, String role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.avatar = avatar;
        this.birthDate = birthDate;
        this.location = location;
        this.job = job;
        this.school = school;
        this.university = university;
        this.status = status;
        this.role = role;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<User> subscribers) {
        this.subscribers = subscribers;
    }

    public List<UserPhoto> getUserPhotos() {
        return userPhotos;
    }

    public void setUserPhotos(List<UserPhoto> userPhotos) {
        this.userPhotos = userPhotos;
    }

    public List<Group> getSubscriptionGroup() {
        return subscriptionGroup;
    }

    public void setSubscriptionGroup(List<Group> subscriptionGroup) {
        this.subscriptionGroup = subscriptionGroup;
    }

    public List<Group> getGroupsModer() {
        return groupsModer;
    }

    public void setGroupsModer(List<Group> groupsModer) {
        this.groupsModer = groupsModer;
    }

    public String getIdString(){
        return String.valueOf(id);
    }

    @Override
    public int hashCode() {
        return this.id;
    }
}
