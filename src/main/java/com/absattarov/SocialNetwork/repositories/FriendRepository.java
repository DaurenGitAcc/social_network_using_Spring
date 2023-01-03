package com.absattarov.SocialNetwork.repositories;

import com.absattarov.SocialNetwork.models.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend,Integer> {
}
