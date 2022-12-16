package com.absattarov.SocialNetwork.services;

import com.absattarov.SocialNetwork.repositories.PostCommentsRepository;
import org.springframework.stereotype.Service;

@Service
public class PostCommentService {
    private final PostCommentsRepository postCommentsRepository;

    public PostCommentService(PostCommentsRepository postCommentsRepository) {
        this.postCommentsRepository = postCommentsRepository;
    }
}
