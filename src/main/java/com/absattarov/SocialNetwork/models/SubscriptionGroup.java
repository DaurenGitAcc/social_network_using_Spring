package com.absattarov.SocialNetwork.models;

import javax.persistence.*;

@Entity
@Table(name = "subscription_group")
public class SubscriptionGroup {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "groups",referencedColumnName = "id")
    private Group group;
    @ManyToOne
    @JoinColumn(name = "subscriber",referencedColumnName = "id")
    private User subscriber;

    public SubscriptionGroup() {
    }

    public SubscriptionGroup(Group group, User subscriber) {
        this.group = group;
        this.subscriber = subscriber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(User subscriber) {
        this.subscriber = subscriber;
    }
}
