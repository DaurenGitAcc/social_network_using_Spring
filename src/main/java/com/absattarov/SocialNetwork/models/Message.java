package com.absattarov.SocialNetwork.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "message")
    private String message;
    @ManyToOne
    @JoinColumn(name = "sender",referencedColumnName = "id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "receiver",referencedColumnName = "id")
    private User receiver;

    public Message() {
    }

    public Message(int id, LocalDateTime createdAt, String message) {
        this.id = id;
        this.createdAt = createdAt;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
