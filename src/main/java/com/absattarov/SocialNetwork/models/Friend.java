package com.absattarov.SocialNetwork.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "friends")
public class Friend {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "users",referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "friend",referencedColumnName = "id")
    private User friend;

    public Friend() {
    }

    public Friend(User user, User friend) {
        this.user = user;
        this.friend = friend;
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

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
}
