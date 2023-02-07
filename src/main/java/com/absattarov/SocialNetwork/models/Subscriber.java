package com.absattarov.SocialNetwork.models;

import javax.persistence.*;

@Entity
@Table(name = "subscriber")
public class Subscriber {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "users",referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "subscriber",referencedColumnName = "id")
    private User subscriber;

    public Subscriber() {
    }

    public Subscriber(User user, User subscriber) {
        this.user = user;
        this.subscriber = subscriber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(User subscriber) {
        this.subscriber = subscriber;
    }
}
