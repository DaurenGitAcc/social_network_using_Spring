package com.absattarov.SocialNetwork.services;

import com.absattarov.SocialNetwork.repositories.UserPhotosRepository;
import org.springframework.stereotype.Service;

@Service
public class UserPhotoService {
    private final UserPhotosRepository userPhotosRepository;

    public UserPhotoService(UserPhotosRepository userPhotosRepository) {
        this.userPhotosRepository = userPhotosRepository;
    }
}
