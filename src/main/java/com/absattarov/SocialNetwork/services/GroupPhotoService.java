package com.absattarov.SocialNetwork.services;

import com.absattarov.SocialNetwork.repositories.GroupPhotosRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupPhotoService {
    private final GroupPhotosRepository groupPhotosRepository;

    public GroupPhotoService(GroupPhotosRepository groupPhotosRepository) {
        this.groupPhotosRepository = groupPhotosRepository;
    }
}
