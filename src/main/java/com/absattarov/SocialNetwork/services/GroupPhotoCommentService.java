package com.absattarov.SocialNetwork.services;

import com.absattarov.SocialNetwork.models.GroupPhotoComment;
import com.absattarov.SocialNetwork.repositories.GroupPhotoCommentsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GroupPhotoCommentService {
    private final GroupPhotoCommentsRepository groupPhotoCommentsRepository;

    public GroupPhotoCommentService(GroupPhotoCommentsRepository groupPhotoCommentsRepository) {
        this.groupPhotoCommentsRepository = groupPhotoCommentsRepository;
    }
    @Transactional
    public void save(GroupPhotoComment groupPhotoComment){
        groupPhotoCommentsRepository.save(groupPhotoComment);
    }
    @Transactional
    public void update(GroupPhotoComment groupPhotoComment){
        groupPhotoCommentsRepository.save(groupPhotoComment);
    }
    @Transactional
    public void delete(int id){
        groupPhotoCommentsRepository.deleteById(id);
    }

    public Optional<GroupPhotoComment> findById(int id){
        return groupPhotoCommentsRepository.findById(id);
    }
}
