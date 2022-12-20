package com.absattarov.SocialNetwork.services;

import com.absattarov.SocialNetwork.models.Post;
import com.absattarov.SocialNetwork.repositories.PostsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class PostService {
    private final PostsRepository postsRepository;

    public PostService(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    @Transactional
    public void save(Post post){
        post.setRating(0);
        post.setCreatedAt(LocalDateTime.now());
        postsRepository.save(post);
    }

}
