package com.absattarov.SocialNetwork.services;

import com.absattarov.SocialNetwork.repositories.UserPhotoCommentsRepository;
import org.springframework.stereotype.Service;

@Service
public class UserPhotoCommentService {
    private final UserPhotoCommentsRepository userPhotoCommentsRepository;

    public UserPhotoCommentService(UserPhotoCommentsRepository userPhotoCommentsRepository) {
        this.userPhotoCommentsRepository = userPhotoCommentsRepository;
    }
}
