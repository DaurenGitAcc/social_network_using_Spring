package com.absattarov.SocialNetwork.repositories;

import com.absattarov.SocialNetwork.models.GroupContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupContactsRepository extends JpaRepository<GroupContact,Integer> {
}
