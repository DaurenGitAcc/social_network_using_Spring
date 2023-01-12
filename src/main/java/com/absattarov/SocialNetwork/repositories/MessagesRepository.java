package com.absattarov.SocialNetwork.repositories;

import com.absattarov.SocialNetwork.models.Message;
import com.absattarov.SocialNetwork.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessagesRepository extends JpaRepository<Message,Integer> {
    List<Message> findBySender(User sender);
    List<Message> findByReceiver(User receiver);
    List<Message> findBySenderAndReceiver(User sender,User receiver);
    Optional<Message> findTopBySenderAndReceiverOrderByCreatedAtDesc(User sender, User receiver);
}
