package com.absattarov.SocialNetwork.repositories;

import com.absattarov.SocialNetwork.models.User;
import com.absattarov.SocialNetwork.models.UserPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPhotosRepository extends JpaRepository<UserPhoto,Integer> {

    Optional<UserPhoto> findTopByOrderByIdDesc();
    Optional<UserPhoto> findByPhotoPath(String path);

}
