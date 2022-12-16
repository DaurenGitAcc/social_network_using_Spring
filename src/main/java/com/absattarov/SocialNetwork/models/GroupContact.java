package com.absattarov.SocialNetwork.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "group_contact")
public class GroupContact {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user",referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "group",referencedColumnName = "id")
    private Group group;
}
