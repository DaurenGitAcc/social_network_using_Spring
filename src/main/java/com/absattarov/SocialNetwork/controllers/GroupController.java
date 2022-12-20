package com.absattarov.SocialNetwork.controllers;

import com.absattarov.SocialNetwork.models.Group;
import com.absattarov.SocialNetwork.models.User;
import com.absattarov.SocialNetwork.security.UserDetails;
import com.absattarov.SocialNetwork.services.GroupService;
import com.absattarov.SocialNetwork.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MODER','ROLE_ADMIN')")
@RequestMapping("/group")
public class GroupController {
    private final UserService userService;
    private final GroupService groupService;

    public GroupController(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByPhoneNumber(userDetails.getUser().getPhoneNumber()).get();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public String toProfile(Model model, @PathVariable(name = "id")int id){
        User currentUser = userService.findById(id).get();
        Group group = groupService.findById(id).get();

        model.addAttribute("currentUser",currentUser);
        model.addAttribute("group",group);
        model.addAttribute("subscribers",group.getMembers());
        model.addAttribute("posts",group.getGroupPosts());
        model.addAttribute("photos",group.getGroupPhotos());
        model.addAttribute("contacts",group.getContacts());
        model.addAttribute("contacts",group.getContacts());

        return "/user/group";
    }

}
