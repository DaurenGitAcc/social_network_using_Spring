package com.absattarov.SocialNetwork.controllers;

import com.absattarov.SocialNetwork.models.Post;
import com.absattarov.SocialNetwork.models.User;
import com.absattarov.SocialNetwork.models.UserPhoto;
import com.absattarov.SocialNetwork.security.UserDetails;
import com.absattarov.SocialNetwork.services.UserPhotoService;
import com.absattarov.SocialNetwork.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MODER','ROLE_ADMIN')")
@RequestMapping("/user")
public class PersonalPageController {
    private final UserService userService;
    private final UserPhotoService userPhotoService;

    public PersonalPageController(UserService userService, UserPhotoService userPhotoService) {
        this.userService = userService;
        this.userPhotoService = userPhotoService;
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByPhoneNumber(userDetails.getUser().getPhoneNumber()).get();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public String toProfile(Model model,@PathVariable(name = "id")int id,
                            @ModelAttribute(name = "post") Post post) throws JsonProcessingException {
        User currentUser = userService.findById(id).get();

        currentUser.getSubscriptionGroup().size();
        currentUser.getFriends().size();
        currentUser.getSubscribers().size();
        currentUser.getPosts().size();
        currentUser.getUserPhotos().size();

        for (Post p : currentUser.getPosts()) {
            System.out.println(p.getJsonn());
        }

        List<Post> postList = currentUser.getPosts();
        List<UserPhoto> userPhotos = currentUser.getUserPhotos();

        for (UserPhoto user_Photo:userPhotos) {
            user_Photo.getUserPhotoComments().size();
        }
        //Collections.sort(postList, Comparator.comparing(Post::getCreatedAt));
        Collections.sort(postList, new Comparator<Post>() {
            public int compare(Post o1, Post o2) {
                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
            }
        });
        Collections.sort(userPhotos, new Comparator<UserPhoto>() {
            public int compare(UserPhoto o1, UserPhoto o2) {
                return o2.getCreatedAt().compareTo(o1.getCreatedAt());
            }
        });

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("authorizedUser",getCurrentUser());
        model.addAttribute("groups", currentUser.getSubscriptionGroup());
        model.addAttribute("friends", currentUser.getFriends());
        model.addAttribute("subscribers", currentUser.getSubscribers());
        model.addAttribute("posts", postList);
        model.addAttribute("photos", currentUser.getUserPhotos());
        model.addAttribute("formt", "%02d");
        model.addAttribute("photos", userPhotos);

//        UserPhoto avatar = userPhotoService.findByPath(currentUser.getAvatar()).get();
//
//        model.addAttribute("avatar", avatar);

        for (UserPhoto userPhoto:currentUser.getUserPhotos()){
            System.out.println(userPhoto.getPhotoPath());
        }

        return "/user/profile";
    }



}
