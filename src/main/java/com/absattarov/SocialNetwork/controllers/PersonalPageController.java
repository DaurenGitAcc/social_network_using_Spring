package com.absattarov.SocialNetwork.controllers;

import com.absattarov.SocialNetwork.models.Post;
import com.absattarov.SocialNetwork.models.User;
import com.absattarov.SocialNetwork.security.UserDetails;
import com.absattarov.SocialNetwork.services.PostService;
import com.absattarov.SocialNetwork.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MODER','ROLE_ADMIN')")
@RequestMapping("/user")
public class PersonalPageController {
    private final UserService userService;

    public PersonalPageController(UserService userService) {
        this.userService = userService;
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByPhoneNumber(userDetails.getUser().getPhoneNumber()).get();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public String toProfile(Model model,@PathVariable(name = "id")int id,
                            @ModelAttribute(name = "post") Post post){
        User currentUser = userService.findById(id).get();

        model.addAttribute("currentUser",currentUser);
        model.addAttribute("groups",currentUser.getSubscriptionGroup());
        model.addAttribute("friends",currentUser.getFriends());
        model.addAttribute("subscribers",currentUser.getSubscribers());
        model.addAttribute("posts",currentUser.getPosts());
        model.addAttribute("photos",currentUser.getUserPhotos());

        return "/user/personal";
    }



}
