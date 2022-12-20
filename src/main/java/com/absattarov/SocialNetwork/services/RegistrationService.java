package com.absattarov.SocialNetwork.services;

import com.absattarov.SocialNetwork.models.User;
import com.absattarov.SocialNetwork.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public void register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        user.setAvatar("https://via.placeholder.com/100");
        user.setStatus("Я новый пользователь!");
        user.setLocation("");
        user.setUniversity("");
        user.setJob("");
        usersRepository.save(user);
    }

}
