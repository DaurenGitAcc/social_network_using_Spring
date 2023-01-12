package com.absattarov.SocialNetwork.repositories;

import com.absattarov.SocialNetwork.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupsRepository extends JpaRepository<Group,Integer> {
    List<Group> findByNameContainingIgnoreCase(String name);
}
