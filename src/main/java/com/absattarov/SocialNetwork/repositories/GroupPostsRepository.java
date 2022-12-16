package com.absattarov.SocialNetwork.repositories;

import com.absattarov.SocialNetwork.models.GroupPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupPostsRepository extends JpaRepository<GroupPost,Integer> {
}
