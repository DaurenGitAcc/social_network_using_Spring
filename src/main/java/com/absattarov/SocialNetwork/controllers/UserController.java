package com.absattarov.SocialNetwork.controllers;

import com.absattarov.SocialNetwork.models.Group;
import com.absattarov.SocialNetwork.models.GroupPost;
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
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByPhoneNumber(userDetails.getUser().getPhoneNumber()).get();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MODER','ROLE_ADMIN')")
    @GetMapping("/")
    @Transactional(readOnly = true)
    public String newsPage(Model model){
        User currentUser = getCurrentUser();
        List<GroupPost> groupPosts = new ArrayList<>();
        List<Post> posts = new ArrayList<>();
        List<Group> groups =  currentUser.getSubscriptionGroup();
        List<User> friends =  currentUser.getFriends();

        System.out.println(groups.isEmpty());

        for (Group group: groups) {
            groupPosts.addAll(group.getGroupPosts());
        }
        for (User friend: friends) {
            posts.addAll(friend.getPosts());
        }
        posts.addAll(currentUser.getPosts());

        model.addAttribute("groupPosts",groupPosts);
        model.addAttribute("posts",posts);

        return "/user/news";
    }

    @GetMapping("/messages")
    public String toMessages(){
        return "/user/messages";
    }
    @GetMapping("/groups")
    public String toGroups(){
        return "/user/groups";
    }
    @GetMapping("/friends")
    public String toFriends(){
        return "/user/friends";
    }


}
