package com.absattarov.SocialNetwork.services;

import com.absattarov.SocialNetwork.repositories.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
}
