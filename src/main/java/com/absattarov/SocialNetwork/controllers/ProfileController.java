package com.absattarov.SocialNetwork.controllers;

import com.absattarov.SocialNetwork.models.*;
import com.absattarov.SocialNetwork.security.UserDetails;
import com.absattarov.SocialNetwork.services.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MODER','ROLE_ADMIN')")
@RequestMapping("/profile")
public class ProfileController {

    private final PostCommentService postCommentService;
    private final UserPhotoService userPhotoService;
    private final UserPhotoCommentService userPhotoCommentService;
    private final PostService postService;
    private final UserService userService;

    public ProfileController(PostCommentService postCommentService, UserPhotoService userPhotoService, UserPhotoCommentService userPhotoCommentService, PostService postService, UserService userService) {
        this.postCommentService = postCommentService;
        this.userPhotoService = userPhotoService;
        this.userPhotoCommentService = userPhotoCommentService;
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

        userPhotos.remove(userPhotos.size()-1);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("authorizedUser",getCurrentUser());
        model.addAttribute("groups", currentUser.getSubscriptionGroup());
        model.addAttribute("friends", currentUser.getFriends());
        model.addAttribute("subscribers", currentUser.getSubscribers());
        model.addAttribute("posts", postList);
        model.addAttribute("photos", currentUser.getUserPhotos());
        model.addAttribute("formt", "%02d");
        model.addAttribute("photos", userPhotos);

        UserPhoto avatar = userPhotoService.findByPath(currentUser.getAvatar()).get();

        model.addAttribute("avatar", avatar);

        for (UserPhoto userPhoto:currentUser.getUserPhotos()){
            System.out.println(userPhoto.getPhotoPath());
        }

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

    @PostMapping("/add-photo-comment")
    public String savePhotoComment(@RequestParam(value = "comment", required = true) String comment,
                                  @RequestParam(value = "user_id", required = true) int user_id,
                                  @RequestParam(value = "photo_id", required = true) int photo_id) {
        UserPhotoComment userPhotoComment = new UserPhotoComment();

        User author = userService.findById(user_id).get();
        UserPhoto userPhoto = userPhotoService.findById(photo_id).get();

        userPhotoComment.setComment(comment);
        userPhotoComment.setUserPhoto(userPhoto);
        userPhotoComment.setAuthor(author);
        userPhotoComment.setCreatedAt(LocalDateTime.now());


        userPhotoCommentService.save(userPhotoComment);

        return "redirect:/profile";
    }
///////////////////////          check this , or prove this

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\target\\classes\\static\\photos";

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".")));
    }
    @PostMapping("/upload")
    public String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {

        int UserId = getCurrentUser().hashCode();
        String directoryName = "user_"+UserId;
        Path path = Paths.get(UPLOAD_DIRECTORY,directoryName);

        if(!Files.exists(path)){
            new File(path.toString()).mkdirs();
        }

        if(Files.exists(path)){
            int PhotoId = userPhotoService.getLastId()+1;
            String photoName = "user_"+UserId+"_photo_"+PhotoId;

            Path fileNameAndPath = Paths.get(path.toString(), photoName+getExtensionByStringHandling(file.getOriginalFilename()).get());
            Files.write(fileNameAndPath, file.getBytes());

            UserPhoto userPhoto = new UserPhoto();
            userPhoto.setUser(getCurrentUser());
            String pathToString=fileNameAndPath.toString().replace("\\","/");
            String cuttedPath=pathToString.substring(pathToString.lastIndexOf("/photos"));
            userPhoto.setPhotoPath(cuttedPath);
            userPhoto.setCreatedAt(LocalDateTime.now());
            userPhoto.setRating(0);
            userPhotoService.save(userPhoto);
        }
        else {
            System.out.println("Upload fails");
        }



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
    @PostMapping("/edit-photo-comment")
    public String editPhotoComment(@RequestParam(value = "comment", required = true) String comment,
                                  @RequestParam(value = "comment_id", required = true) int comment_id) {

        UserPhotoComment userPhotoComment = userPhotoCommentService.findById(comment_id).get();
        userPhotoComment.setComment(comment);
        userPhotoCommentService.update(userPhotoComment);

        return "redirect:/profile";
    }

    @PostMapping("/set-as-avatar")
    public String setAvatar(@RequestParam(value = "user_id", required = true) int user_id,
                                   @RequestParam(value = "photo_id", required = true) int photo_id) {

        User user = userService.findById(user_id).get();
        UserPhoto userPhoto = userPhotoService.findById(photo_id).get();

        user.setAvatar(userPhoto.getPhotoPath());
        userService.update(user);

        return "redirect:/profile";
    }

    @PostMapping("/delete-post-comment")
    public String deletePostComment(@RequestParam(value = "comment_id", required = true) int comment_id) {

        postCommentService.deleteById(comment_id);

        return "redirect:/profile";
    }
    @PostMapping("/delete-user-photo")
    public String deletePhoto(@RequestParam(value = "photo_id", required = true) int photo_id) {

        // the photo won't be deleted from the folder

        userPhotoService.delete(photo_id);

        return "redirect:/profile";
    }
    @PostMapping("/delete-photo-comment")
    public String deletePhotoComment(@RequestParam(value = "comment_id", required = true) int comment_id) {

        userPhotoCommentService.delete(comment_id);

        return "redirect:/profile";
    }

    @PostMapping("/like-photo")
    public String likePhoto(@RequestParam(value = "photo_id", required = true) int photo_id) {

        UserPhoto userPhoto = userPhotoService.findById(photo_id).get();

        userPhoto.setRating(userPhoto.getRating()+1);


        userPhotoService.update(userPhoto);

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

    @PostMapping("/like-post")
    public String likePost(@RequestParam(value = "post_id", required = true) int post_id) {

        Post post = postService.findById(post_id).get();

        post.setRating(post.getRating()+1);


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
