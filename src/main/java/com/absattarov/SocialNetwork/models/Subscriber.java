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
    @JoinColumn(name = "user",referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "subscriber",referencedColumnName = "id")
    private User subscriber;
}
