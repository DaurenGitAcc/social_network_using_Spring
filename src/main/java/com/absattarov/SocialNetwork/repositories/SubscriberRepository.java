package com.absattarov.SocialNetwork.repositories;

import com.absattarov.SocialNetwork.models.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber,Integer> {
}
