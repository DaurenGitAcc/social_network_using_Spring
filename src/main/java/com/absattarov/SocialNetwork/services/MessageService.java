package com.absattarov.SocialNetwork.services;

import com.absattarov.SocialNetwork.repositories.MessagesRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessagesRepository messagesRepository;

    public MessageService(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }


}
