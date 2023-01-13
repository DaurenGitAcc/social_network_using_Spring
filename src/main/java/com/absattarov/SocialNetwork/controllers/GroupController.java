package com.absattarov.SocialNetwork.controllers;

import com.absattarov.SocialNetwork.models.*;
import com.absattarov.SocialNetwork.security.UserDetails;
import com.absattarov.SocialNetwork.services.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MODER','ROLE_ADMIN')")
@RequestMapping("/groups")
public class GroupController {
    private final UserService userService;
    private final GroupService groupService;
    private final GroupPhotoService groupPhotoService;
    private final GroupPhotoCommentService groupPhotoCommentService;
    private final GroupPostService groupPostService;
    private final GroupPostCommentService groupPostCommentService;

    private final GroupContactService groupContactService;

    private final SubscriptionsService subscriptionsService;

    public GroupController(UserService userService, GroupService groupService, GroupPhotoService groupPhotoService, GroupPhotoCommentService groupPhotoCommentService, GroupPostService groupPostService, GroupPostCommentService groupPostCommentService, GroupContactService groupContactService, SubscriptionsService subscriptionsService) {
        this.userService = userService;
        this.groupService = groupService;
        this.groupPhotoService = groupPhotoService;
        this.groupPhotoCommentService = groupPhotoCommentService;
        this.groupPostService = groupPostService;
        this.groupPostCommentService = groupPostCommentService;
        this.groupContactService = groupContactService;
        this.subscriptionsService = subscriptionsService;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByPhoneNumber(userDetails.getUser().getPhoneNumber()).get();
    }

    @GetMapping("")
    public String groups(Model model, @ModelAttribute(name = "newGroup") Group newGroup,
                         @RequestParam(value = "q", defaultValue = "") String searchLine) {
        User authorizedUser = getCurrentUser();

        List<Group> subscription = new ArrayList<>();
        List<Group> searchResult = new ArrayList<>();
        Set<Integer> subscribedGroupsId = new HashSet<>();

        if (searchLine.isEmpty()) {
            subscription = authorizedUser.getSubscriptionGroup();
        } else {
            searchResult = groupService.findByNameContaining(searchLine);
            for (Group g : authorizedUser.getSubscriptionGroup()) {
                subscribedGroupsId.add(g.getId());
            }
        }


        model.addAttribute("subscriptions", subscription);
        model.addAttribute("searchResults", searchResult);
        model.addAttribute("subscribedGroupsId", subscribedGroupsId);
        model.addAttribute("authorizedUser", authorizedUser);

        return "/user/groups";
    }

    @PostMapping("/subscription")
    public String subscribeGroup(@RequestParam(value = "user_id", defaultValue = "") int user_id,
                                 @RequestParam(value = "group_id", defaultValue = "") int group_id) {
        SubscriptionGroup subscriptionGroup = new SubscriptionGroup();
        subscriptionGroup.setSubscriber(userService.findById(user_id).get());
        subscriptionGroup.setGroup(groupService.findById(group_id).get());
        subscriptionsService.save(subscriptionGroup);


        return "redirect:/groups";
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public String toProfile(Model model, @PathVariable(name = "id") int id) {
        User authorizedUser = getCurrentUser();
        Group currentGroup = groupService.findById(id).get();
        Set<Integer> groupContactsId = new HashSet<>();
        Set<Integer> groupSubscribersId = new HashSet<>();

        for (User contact : currentGroup.getContacts()) {
            groupContactsId.add(contact.getId());
        }
        for (User contact : currentGroup.getMembers()) {
            groupSubscribersId.add(contact.getId());
        }

        List<GroupPhoto> groupPhotos = currentGroup.getGroupPhotos();

        for (GroupPhoto groupPhoto : groupPhotos) {
            groupPhoto.getGroupPhotoComments().size();
        }

        // GroupPhoto groupAvatar = groupPhotoService.findByPath(currentGroup.getAvatar()).get();

        model.addAttribute("authorizedUser", authorizedUser);
        model.addAttribute("currentGroup", currentGroup);
        //model.addAttribute("groupAvatar",groupAvatar);
        model.addAttribute("posts", currentGroup.getGroupPosts());
        model.addAttribute("photos", groupPhotos);

        model.addAttribute("contacts", currentGroup.getContacts());
        model.addAttribute("groupContactsId", groupContactsId);
        model.addAttribute("subscribers", currentGroup.getMembers());
        model.addAttribute("groupSubscribersId", groupSubscribersId);


        return "/user/group";
    }

    @PostMapping("/groupInfo")
    public String saveInfo(@RequestParam(value = "description", defaultValue = "") String description,
                           @RequestParam(value = "group_id", defaultValue = "") int group_id,
                           @RequestParam(value = "contactId", defaultValue = "-1") int contact) {

        Group currentGroup = groupService.findById(group_id).get();
        currentGroup.setDescription(description);

        if(contact>=0){
            GroupContact groupContact = new GroupContact();            // insert in group's contacts
            groupContact.setGroup(groupService.findById(group_id).get());
            groupContact.setUser(userService.findById(contact).get());
            groupContactService.save(groupContact);
        }

        groupService.update(currentGroup);


        return "redirect:/groups/" + group_id;
    }

    public static String UPLOAD_DIRECTORY_GROUP_DEFAULT_AVATAR = System.getProperty("user.dir") + "\\target\\classes\\static\\group_photos";

    @PostMapping("/create-group")
    public String createGroup(@ModelAttribute(name = "newGroup") Group newGroup) throws IOException {

        int savedGroupId = groupService.save(newGroup);

        ///

        int groupId = groupService.findById(savedGroupId).get().hashCode();
        String directoryName = "group_" + groupId;
        Path path = Paths.get(UPLOAD_DIRECTORY_GROUP_DEFAULT_AVATAR, directoryName);

        if (!Files.exists(path)) {
            new File(path.toString()).mkdirs();
        }

        if (Files.exists(path)) {
            String photoName = "default";

            Path fileNameAndPath = Paths.get(path.toString(), photoName + ".png");

            String fileNameAndPathToString = fileNameAndPath.toString().replace("\\", "/");
            String cuttedPath = fileNameAndPathToString.substring(fileNameAndPathToString.lastIndexOf("/target"));

            writePhoto(cuttedPath.substring(1));
            cuttedPath = cuttedPath.substring(cuttedPath.lastIndexOf("/group_photos"));

            GroupPhoto photo = new GroupPhoto();
            photo.setGroup(groupService.findById(savedGroupId).get());

            photo.setPhotoPath(cuttedPath);
            photo.setCreatedAt(LocalDateTime.now());
            photo.setRating(0);
            groupPhotoService.save(photo);
            ///
            Group group = groupService.findById(savedGroupId).get();      // set avatar of group
            group.setAvatar(cuttedPath);
            groupService.update(group);


            SubscriptionGroup subscriptionGroup = new SubscriptionGroup();  // subscription
            subscriptionGroup.setSubscriber(getCurrentUser());
            subscriptionGroup.setGroup(groupService.findById(savedGroupId).get());
            subscriptionsService.save(subscriptionGroup);

            GroupContact groupContact = new GroupContact();            // insert in group's contacts
            groupContact.setGroup(groupService.findById(savedGroupId).get());
            groupContact.setUser(getCurrentUser());
            groupContactService.save(groupContact);

        } else {
            System.out.println("Upload fails");
        }
        ///

        return "redirect:/groups/" + savedGroupId;
    }

    private void writePhoto(String path) throws IOException {
        File copied = new File(path);
        try (
                InputStream in = new BufferedInputStream(
                        new FileInputStream("src/main/resources/static/img/default-avatar-group.png"));
                OutputStream out = new BufferedOutputStream(
                        new FileOutputStream(copied))) {

            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        }
    }

    @PostMapping("/post")
    public String savePost(@RequestParam(value = "group_id", defaultValue = "") int group_id,
                           @RequestParam(value = "post", required = true) String postText) {
        GroupPost newPost = new GroupPost();

        newPost.setGroup(groupService.findById(group_id).get());
        newPost.setPost(postText);
        groupPostService.save(newPost);

        return "redirect:/groups/" + group_id;
    }

    @PostMapping("/delete-post")
    public String deletePost(@RequestParam(value = "post_id", required = true) int post_id,
                             @RequestParam(value = "group_id", required = true) int group_id) {
        groupPostService.deleteById(post_id);
        return "redirect:/groups/" + group_id;
    }

    @PostMapping("/edit-post")
    public String editPost(@RequestParam(value = "post", required = true) String postUpdated,
                           @RequestParam(value = "group_id", required = true) int group_id,
                           @RequestParam(value = "post_id", required = true) int post_id) {

        GroupPost post = groupPostService.findById(post_id).get();

        post.setPost(postUpdated);


        groupPostService.update(post);

        return "redirect:/groups/" + group_id;
    }

    @PostMapping("/like-post")
    public String likePost(@RequestParam(value = "post_id", required = true) int post_id,
                           @RequestParam(value = "group_id", required = true) int group_id) {
        GroupPost post = groupPostService.findById(post_id).get();
        post.setRating(post.getRating() + 1);
        groupPostService.update(post);
        return "redirect:/groups/" + group_id;
    }

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\target\\classes\\static\\group_photos";

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".")));
    }

    @PostMapping("/upload")
    public String uploadImage(Model model, @RequestParam("image") MultipartFile file,
                              @RequestParam(value = "group_id", defaultValue = "") int group_id) throws IOException {
        Group currentGroup = groupService.findById(group_id).get();

        int GroupId = currentGroup.hashCode();
        String directoryName = "group_" + GroupId;
        Path path = Paths.get(UPLOAD_DIRECTORY, directoryName);

        if (!Files.exists(path)) {
            new File(path.toString()).mkdirs();
        }

        if (Files.exists(path)) {
            int PhotoId = groupPhotoService.getLastId() + 1;
            String photoName = "group_" + GroupId + "_photo_" + PhotoId;

            Path fileNameAndPath = Paths.get(path.toString(), photoName + getExtensionByStringHandling(file.getOriginalFilename()).get());
            Files.write(fileNameAndPath, file.getBytes());

            GroupPhoto groupPhoto = new GroupPhoto();
            groupPhoto.setGroup(currentGroup);
            String pathToString = fileNameAndPath.toString().replace("\\", "/");
            String cuttedPath = pathToString.substring(pathToString.lastIndexOf("/group_photos"));
            groupPhoto.setPhotoPath(cuttedPath);
            groupPhoto.setCreatedAt(LocalDateTime.now());
            groupPhoto.setRating(0);
            groupPhotoService.save(groupPhoto);
        } else {
            System.out.println("Upload fails");
        }


        return "redirect:/groups/" + group_id;
    }

    @PostMapping("/set-as-avatar")
    public String setAvatar(@RequestParam(value = "group_id", required = true) int group_id,
                            @RequestParam(value = "photo_id", required = true) int photo_id) {

        Group group = groupService.findById(group_id).get();
        GroupPhoto photo = groupPhotoService.findById(photo_id).get();

        group.setAvatar(photo.getPhotoPath());
        groupService.update(group);

        return "redirect:/groups/" + group_id;
    }

    @PostMapping("/delete-user-photo")
    public String deletePhoto(@RequestParam(value = "group_id", required = true) int group_id,
                              @RequestParam(value = "photo_id", required = true) int photo_id) {

        // the photo won't be deleted from the folder

        groupPhotoService.delete(photo_id);

        return "redirect:/groups/" + group_id;
    }

    @PostMapping("/add-photo-comment")
    public String savePhotoComment(@RequestParam(value = "comment", required = true) String comment,
                                   @RequestParam(value = "user_id", required = true) int user_id,
                                   @RequestParam(value = "group_id", required = true) int group_id,
                                   @RequestParam(value = "photo_id", required = true) int photo_id) {
        GroupPhotoComment photoComment = new GroupPhotoComment();
        User author = userService.findById(user_id).get();
        GroupPhoto groupPhoto = groupPhotoService.findById(photo_id).get();
        photoComment.setComment(comment);
        photoComment.setAuthor(author);
        photoComment.setCreatedAt(LocalDateTime.now());
        photoComment.setGroupPhoto(groupPhoto);
        groupPhotoCommentService.save(photoComment);

        return "redirect:/groups/" + group_id;
    }

    @PostMapping("/delete-photo-comment")
    public String deletePhotoComment(@RequestParam(value = "comment_id", required = true) int comment_id,
                                     @RequestParam(value = "group_id", required = true) int group_id) {

        groupPhotoCommentService.delete(comment_id);

        return "redirect:/groups/" + group_id;
    }

    @PostMapping("/edit-photo-comment")
    public String editPhotoComment(@RequestParam(value = "comment", required = true) String comment,
                                   @RequestParam(value = "comment_id", required = true) int comment_id,
                                   @RequestParam(value = "group_id", required = true) int group_id) {

        GroupPhotoComment photoComment = groupPhotoCommentService.findById(comment_id).get();
        photoComment.setComment(comment);
        groupPhotoCommentService.update(photoComment);

        return "redirect:/groups/" + group_id;
    }

    @PostMapping("/add-post-comment")
    public String savePostComment(@RequestParam(value = "comment", required = true) String comment,
                                  @RequestParam(value = "user_id", required = true) int user_id,
                                  @RequestParam(value = "group_id", required = true) int group_id,
                                  @RequestParam(value = "post_id", required = true) int post_id) {
        GroupPostComment postComment = new GroupPostComment();
        User author = userService.findById(user_id).get();
        GroupPost groupPost = groupPostService.findById(post_id).get();
        postComment.setComment(comment);
        postComment.setAuthor(author);
        postComment.setGroupPost(groupPost);
        postComment.setCreatedAt(LocalDateTime.now());
        groupPostCommentService.save(postComment);

        return "redirect:/groups/" + group_id;
    }

    @PostMapping("/edit-post-comment")
    public String editPostComment(@RequestParam(value = "comment", required = true) String comment,
                                  @RequestParam(value = "comment_id", required = true) int comment_id,
                                  @RequestParam(value = "group_id", required = true) int group_id) {

        GroupPostComment postComment = groupPostCommentService.findById(comment_id).get();
        postComment.setComment(comment);
        groupPostCommentService.update(postComment);

        return "redirect:/groups/" + group_id;
    }

    @PostMapping("/delete-post-comment")
    public String deletePostComment(@RequestParam(value = "comment_id", required = true) int comment_id,
                                    @RequestParam(value = "group_id", required = true) int group_id) {

        groupPostCommentService.delete(comment_id);

        return "redirect:/groups/" + group_id;
    }


}
