package com.absattarov.SocialNetwork.utils;

import com.absattarov.SocialNetwork.models.User;
import com.absattarov.SocialNetwork.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserDetailsService userDetailsService;

    @Autowired
    public UserValidator(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user=(User) target;
        try {
            userDetailsService.loadUserByUsername(user.getPhoneNumber());
        }catch (UsernameNotFoundException ignore){
            return;
        }
        errors.rejectValue("phoneNumber","","User with such phone number already exist!");
    }
}
