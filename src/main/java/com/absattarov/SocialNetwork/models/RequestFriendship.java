package com.absattarov.SocialNetwork.models;

import javax.persistence.*;

@Entity
@Table(name = "request_friendship")
public class RequestFriendship {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "sender",referencedColumnName = "id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "receiver",referencedColumnName = "id")
    private User receiver;

    public RequestFriendship() {
    }

    public RequestFriendship(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
