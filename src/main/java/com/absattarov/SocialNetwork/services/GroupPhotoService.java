package com.absattarov.SocialNetwork.services;

import com.absattarov.SocialNetwork.models.GroupPhoto;
import com.absattarov.SocialNetwork.models.UserPhoto;
import com.absattarov.SocialNetwork.repositories.GroupPhotosRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class GroupPhotoService {
    private final GroupPhotosRepository groupPhotosRepository;

    public GroupPhotoService(GroupPhotosRepository groupPhotosRepository) {
        this.groupPhotosRepository = groupPhotosRepository;
    }

    public Optional<GroupPhoto> findById(int id){
        return groupPhotosRepository.findById(id);
    }
    public Optional<GroupPhoto> findByPath(String path){
        return groupPhotosRepository.findByPhotoPath(path);
    }
    public int getLastId(){
        Optional<GroupPhoto> groupPhoto=groupPhotosRepository.findTopByOrderByIdDesc();
        if (groupPhoto.isPresent()){
            return groupPhoto.get().getId();
        }
        else return -1;
    }

    @Transactional
    public void save(GroupPhoto groupPhoto){
        groupPhotosRepository.save(groupPhoto);
    }

    @Transactional
    public void delete(int id){
        groupPhotosRepository.deleteById(id);
    }
}
