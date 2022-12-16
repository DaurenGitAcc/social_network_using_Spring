package com.absattarov.SocialNetwork.repositories;

import com.absattarov.SocialNetwork.models.UserPhotoComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPhotoCommentsRepository extends JpaRepository<UserPhotoComment,Integer> {
}
