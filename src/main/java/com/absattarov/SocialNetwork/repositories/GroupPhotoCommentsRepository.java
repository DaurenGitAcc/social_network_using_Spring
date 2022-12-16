package com.absattarov.SocialNetwork.repositories;

import com.absattarov.SocialNetwork.models.GroupPhotoComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupPhotoCommentsRepository extends JpaRepository<GroupPhotoComment,Integer> {
}
