package com.absattarov.SocialNetwork.services;

import com.absattarov.SocialNetwork.repositories.PostsRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostsRepository postsRepository;

    public PostService(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }
}
