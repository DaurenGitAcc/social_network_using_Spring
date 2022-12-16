package com.absattarov.SocialNetwork.services;

import com.absattarov.SocialNetwork.models.Group;
import com.absattarov.SocialNetwork.repositories.GroupsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GroupService {
    private final GroupsRepository groupsRepository;

    public GroupService(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }

}
