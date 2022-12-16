package com.absattarov.SocialNetwork.services;

import com.absattarov.SocialNetwork.repositories.GroupPostCommentsRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupPostCommentService {
    private final GroupPostCommentsRepository groupPostCommentsRepository;

    public GroupPostCommentService(GroupPostCommentsRepository groupPostCommentsRepository) {
        this.groupPostCommentsRepository = groupPostCommentsRepository;
    }
}
