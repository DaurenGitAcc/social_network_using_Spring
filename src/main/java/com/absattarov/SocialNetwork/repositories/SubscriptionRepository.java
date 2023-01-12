package com.absattarov.SocialNetwork.repositories;

import com.absattarov.SocialNetwork.models.SubscriptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionGroup,Integer> {
}
