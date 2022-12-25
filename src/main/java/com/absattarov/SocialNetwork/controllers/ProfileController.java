package com.absattarov.SocialNetwork.controllers;

import com.absattarov.SocialNetwork.models.Post;
import com.absattarov.SocialNetwork.models.PostComment;
import com.absattarov.SocialNetwork.models.User;
import com.absattarov.SocialNetwork.security.UserDetails;
import com.absattarov.SocialNetwork.services.PostCommentService;
import com.absattarov.SocialNetwork.services.PostService;
import com.absattarov.SocialNetwork.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MODER','ROLE_ADMIN')")
@RequestMapping("/profile")
public class ProfileController {

    private final PostCommentService postCommentService;

    private final PostService postService;
    private final UserService userService;

    public ProfileController(PostCommentService postCommentService, PostService postService, UserService userService) {
        this.postCommentService = postCommentService;
        this.postService = postService;
        this.userService = userService;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByPhoneNumber(userDetails.getUser().getPhoneNumber()).get();
    }


    @GetMapping
    @Transactional(readOnly = true)
    public String toProfile(Model model, @ModelAttribute(name = "post") Post post) throws JsonProcessingException {
        User currentUser = getCurrentUser();

        currentUser.getSubscriptionGroup().size();
        currentUser.getFriends().size();
        currentUser.getSubscribers().size();
        currentUser.getPosts().size();
        currentUser.getUserPhotos().size();

        for (Post p : currentUser.getPosts()) {
            System.out.println(p.getJsonn());
        }

        List<Post> postList = currentUser.getPosts();
        //Collections.sort(postList, Comparator.comparing(Post::getCreatedAt));
        Collections.sort(postList, new Comparator<Post>() {
            public int compare(Post o1, Post o2) {
                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
            }
        });

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("groups", currentUser.getSubscriptionGroup());
        model.addAttribute("friends", currentUser.getFriends());
        model.addAttribute("subscribers", currentUser.getSubscribers());
        model.addAttribute("posts", postList);
        model.addAttribute("photos", currentUser.getUserPhotos());
        model.addAttribute("formt", "%02d");

        return "/user/profile";
    }

    @PostMapping("/post")
    public String savePost(@RequestParam(value = "post", required = true) String postText) {
        Post newPost = new Post();

        newPost.setAuthor(getCurrentUser());
        newPost.setPost(postText);
        postService.save(newPost);

        return "redirect:/profile";
    }

    @PostMapping("/personalInfo")
    public String saveInfo(@ModelAttribute(name = "currentUser") User userWithUpdatedInfo) {
        User currentUser = getCurrentUser();
        currentUser.setStatus(userWithUpdatedInfo.getStatus());
        currentUser.setUniversity(userWithUpdatedInfo.getUniversity());
        currentUser.setLocation(userWithUpdatedInfo.getLocation());
        currentUser.setBirthDate(userWithUpdatedInfo.getBirthDate());
        currentUser.setJob(userWithUpdatedInfo.getJob());

        userService.update(currentUser);

        return "redirect:/profile";
    }

    @PostMapping("/add-post-comment")
    public String savePostComment(@RequestParam(value = "comment", required = true) String comment,
                                  @RequestParam(value = "user_id", required = true) int user_id,
                                  @RequestParam(value = "post_id", required = true) int post_id) {
        PostComment postComment = new PostComment();

        User author = userService.findById(user_id).get();
        Post post = postService.findById(post_id).get();

        postComment.setComment(comment);
        postComment.setAuthor(author);
        postComment.setPost(post);


        postCommentService.save(postComment);

        return "redirect:/profile";
    }

    @PostMapping("/edit-post-comment")
    public String editPostComment(@RequestParam(value = "comment", required = true) String comment,
                                  @RequestParam(value = "comment_id", required = true) int comment_id) {

        PostComment postComment = postCommentService.findById(comment_id).get();
        postComment.setComment(comment);
        postCommentService.update(postComment);

        return "redirect:/profile";
    }
    @PostMapping("/delete-post-comment")
    public String deletePostComment(@RequestParam(value = "comment_id", required = true) int comment_id) {

        postCommentService.deleteById(comment_id);

        return "redirect:/profile";
    }

    @PostMapping("/edit-post")
    public String editPost(@RequestParam(value = "post", required = true) String postUpdated,
                           @RequestParam(value = "user_id", required = true) int user_id,
                           @RequestParam(value = "post_id", required = true) int post_id) {

        User author = userService.findById(user_id).get();
        Post post = postService.findById(post_id).get();

        post.setPost(postUpdated);


        postService.updated(post);

        return "redirect:/profile";
    }

    @PostMapping("/delete-post")
    public String deletePost(@RequestParam(value = "post_id", required = true) int post_id) {
        postService.deleteById(post_id);
        return "redirect:/profile";
    }

    @GetMapping("/test")
    public String toProfile() {

        return "/user/test-JS-coping";
    }

}
