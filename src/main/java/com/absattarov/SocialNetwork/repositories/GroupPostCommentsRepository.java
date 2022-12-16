package com.absattarov.SocialNetwork.repositories;

import com.absattarov.SocialNetwork.models.GroupPostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupPostCommentsRepository extends JpaRepository<GroupPostComment,Integer> {
}
