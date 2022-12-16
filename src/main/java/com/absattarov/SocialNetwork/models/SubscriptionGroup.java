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
    @JoinColumn(name = "group",referencedColumnName = "id")
    private Group group;
    @ManyToOne
    @JoinColumn(name = "subscriber",referencedColumnName = "id")
    private User subscriber;


}
