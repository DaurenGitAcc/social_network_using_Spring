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
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MODER','ROLE_ADMIN')")
@RequestMapping("/profile")
public class ProfileController {

    private final PostService postService;
    private final UserService userService;

    public ProfileController( PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByPhoneNumber(userDetails.getUser().getPhoneNumber()).get();
    }


    @GetMapping
    @Transactional(readOnly = true)
    public String toProfile(Model model, @ModelAttribute(name = "post")Post post){
        User currentUser = getCurrentUser();

        model.addAttribute("currentUser",currentUser);
        model.addAttribute("groups",currentUser.getSubscriptionGroup());
        model.addAttribute("friends",currentUser.getFriends());
        model.addAttribute("subscribers",currentUser.getSubscribers());
        model.addAttribute("posts",currentUser.getPosts());
        model.addAttribute("photos",currentUser.getUserPhotos());

        return "/user/profile";
    }

    @PostMapping("/post")
    public String savePost(@RequestParam(value = "post", required = true) String postText){
        Post newPost= new Post();

        newPost.setAuthor(getCurrentUser());
        newPost.setPost(postText);
        postService.save(newPost);

        return "redirect:/profile";
    }

    @PostMapping("/personalInfo")
    public String saveInfo(@ModelAttribute(name = "currentUser")User userWithUpdatedInfo){
        User currentUser=getCurrentUser();
        currentUser.setStatus(userWithUpdatedInfo.getStatus());
        currentUser.setUniversity(userWithUpdatedInfo.getUniversity());
        currentUser.setLocation(userWithUpdatedInfo.getLocation());
        currentUser.setBirthDate(userWithUpdatedInfo.getBirthDate());
        currentUser.setJob(userWithUpdatedInfo.getJob());

        userService.update(currentUser);

        return "redirect:/profile";
    }

}
