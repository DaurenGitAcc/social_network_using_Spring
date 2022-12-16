package com.absattarov.SocialNetwork.repositories;

import com.absattarov.SocialNetwork.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Post,Integer> {
}
