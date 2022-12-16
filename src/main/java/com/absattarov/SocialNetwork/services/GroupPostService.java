package com.absattarov.SocialNetwork.services;

import com.absattarov.SocialNetwork.repositories.GroupPostsRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupPostService {
    private final GroupPostsRepository groupPostsRepository;

    public GroupPostService(GroupPostsRepository groupPostsRepository) {
        this.groupPostsRepository = groupPostsRepository;
    }
}
