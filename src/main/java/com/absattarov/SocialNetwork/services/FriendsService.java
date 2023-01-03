package com.absattarov.SocialNetwork.services;

import com.absattarov.SocialNetwork.models.Friend;
import com.absattarov.SocialNetwork.repositories.FriendRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FriendsService {
    private final FriendRepository friendRepository;

    public FriendsService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    @Transactional
    public void save(Friend friend){
        friendRepository.save(friend);
    }
}
