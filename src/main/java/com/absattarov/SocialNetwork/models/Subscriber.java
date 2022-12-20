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
    private User user;
    @ManyToOne
    private User subscriber;
}
