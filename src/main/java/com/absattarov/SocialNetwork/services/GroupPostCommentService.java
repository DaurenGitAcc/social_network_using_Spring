package com.absattarov.SocialNetwork.services;

import com.absattarov.SocialNetwork.models.GroupPhotoComment;
import com.absattarov.SocialNetwork.models.GroupPostComment;
import com.absattarov.SocialNetwork.repositories.GroupPostCommentsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GroupPostCommentService {
    private final GroupPostCommentsRepository groupPostCommentsRepository;

    public GroupPostCommentService(GroupPostCommentsRepository groupPostCommentsRepository) {
        this.groupPostCommentsRepository = groupPostCommentsRepository;
    }

    @Transactional
    public void save(GroupPostComment groupPostComment){
        groupPostCommentsRepository.save(groupPostComment);
    }
    @Transactional
    public void update(GroupPostComment groupPostComment){
        groupPostCommentsRepository.save(groupPostComment);
    }
    @Transactional
    public void delete(int id){
        groupPostCommentsRepository.deleteById(id);
    }
    public Optional<GroupPostComment> findById(int id){
        return groupPostCommentsRepository.findById(id);
    }
}
