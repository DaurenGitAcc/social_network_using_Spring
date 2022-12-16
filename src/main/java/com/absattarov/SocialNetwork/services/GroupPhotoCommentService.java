package com.absattarov.SocialNetwork.services;

import com.absattarov.SocialNetwork.repositories.GroupPhotoCommentsRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupPhotoCommentService {
    private final GroupPhotoCommentsRepository groupPhotoCommentsRepository;

    public GroupPhotoCommentService(GroupPhotoCommentsRepository groupPhotoCommentsRepository) {
        this.groupPhotoCommentsRepository = groupPhotoCommentsRepository;
    }
}
