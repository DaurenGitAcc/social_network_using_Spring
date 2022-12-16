package com.absattarov.SocialNetwork.repositories;

import com.absattarov.SocialNetwork.models.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentsRepository extends JpaRepository<PostComment,Integer> {
}
