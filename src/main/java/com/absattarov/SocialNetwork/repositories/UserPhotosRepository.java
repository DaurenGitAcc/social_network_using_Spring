package com.absattarov.SocialNetwork.repositories;

import com.absattarov.SocialNetwork.models.UserPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPhotosRepository extends JpaRepository<UserPhoto,Integer> {
}
