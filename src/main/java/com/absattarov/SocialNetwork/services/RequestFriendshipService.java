package com.absattarov.SocialNetwork.services;

import com.absattarov.SocialNetwork.models.RequestFriendship;
import com.absattarov.SocialNetwork.models.User;
import com.absattarov.SocialNetwork.repositories.RequestsFriendshipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RequestFriendshipService {
    private final RequestsFriendshipRepository requestsFriendshipRepository;

    public RequestFriendshipService(RequestsFriendshipRepository requestsFriendshipRepository) {
        this.requestsFriendshipRepository = requestsFriendshipRepository;
    }

    @Transactional
    public void save(RequestFriendship requestFriendship){
        requestsFriendshipRepository.save(requestFriendship);
    }

    public List<User> findBySender(User sender){
        List<User> receiversList = new ArrayList<>();
        requestsFriendshipRepository.findBySender(sender).forEach((c) -> receiversList.add(c.getReceiver()));;
        return receiversList;
    }
    public List<User> findByReceiver(User receiver){
        List<User> sendersList = new ArrayList<>();
        requestsFriendshipRepository.findByReceiver(receiver).forEach((c) -> sendersList.add(c.getSender()));;
        return sendersList;
    }

    public void deleteBySenderAndReceiver(User sender, User receiver){
        requestsFriendshipRepository.deleteBySenderAndReceiver(sender, receiver);
    }
}
