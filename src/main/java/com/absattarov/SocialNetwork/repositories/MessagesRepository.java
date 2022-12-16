package com.absattarov.SocialNetwork.repositories;

import com.absattarov.SocialNetwork.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends JpaRepository<Message,Integer> {
}
