package com.absattarov.SocialNetwork.services;

import com.absattarov.SocialNetwork.models.Group;
import com.absattarov.SocialNetwork.repositories.GroupsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GroupService {
    private final GroupsRepository groupsRepository;

    public GroupService(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }
    public Optional<Group> findById(int id){
        return groupsRepository.findById(id);
    }

    public List<Group> findByNameContaining(String name){
        return groupsRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional
    public void save(Group group){
        groupsRepository.save(group);
    }
    @Transactional
    public void update(Group group){
        groupsRepository.save(group);
    }

}
