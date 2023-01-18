package com.absattarov.SocialNetwork.controllers;

import com.absattarov.SocialNetwork.DTO.GroupPostDTO;
import com.absattarov.SocialNetwork.DTO.PostDTO;
import com.absattarov.SocialNetwork.DTO.UserPostDTO;
import com.absattarov.SocialNetwork.models.Group;
import com.absattarov.SocialNetwork.models.GroupPost;
import com.absattarov.SocialNetwork.models.Post;
import com.absattarov.SocialNetwork.models.User;
import com.absattarov.SocialNetwork.security.UserDetails;
import com.absattarov.SocialNetwork.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByPhoneNumber(userDetails.getUser().getPhoneNumber()).get();
    }

    private UserPostDTO UserPostToUserPostDTO(Post post) {
        return modelMapper.map(post, UserPostDTO.class);
    }
    private GroupPostDTO GroupPostToGroupPostDTO(GroupPost groupPost) {
        return modelMapper.map(groupPost, GroupPostDTO.class);
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

        List<UserPostDTO> userPostsDTO = new ArrayList<>();
        List<GroupPostDTO> groupPostsDTO = new ArrayList<>();

        for (Post post: posts) {
            userPostsDTO.add(UserPostToUserPostDTO(post));
        }
        for (GroupPost post: groupPosts) {
            groupPostsDTO.add(GroupPostToGroupPostDTO(post));
        }

        List<PostDTO> postss = new ArrayList<>();
        postss.addAll(userPostsDTO);
        postss.addAll(groupPostsDTO);

        Collections.sort(postss, new Comparator<PostDTO>() {
            public int compare(PostDTO o1, PostDTO o2) {
                return o2.getCreatedAt().compareTo(o1.getCreatedAt());
            }
        });


        model.addAttribute("posts",postss);

        return "/user/news";
    }




}
