package com.absattarov.SocialNetwork.services;

import com.absattarov.SocialNetwork.models.SubscriptionGroup;
import com.absattarov.SocialNetwork.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SubscriptionsService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionsService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Transactional
    public void save(SubscriptionGroup subscriptionGroup){
        subscriptionRepository.save(subscriptionGroup);
    }


}
