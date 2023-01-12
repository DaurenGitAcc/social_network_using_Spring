package com.absattarov.SocialNetwork.controllers;

import com.absattarov.SocialNetwork.models.Message;
import com.absattarov.SocialNetwork.models.Post;
import com.absattarov.SocialNetwork.models.User;
import com.absattarov.SocialNetwork.security.UserDetails;
import com.absattarov.SocialNetwork.services.MessagesService;
import com.absattarov.SocialNetwork.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MODER','ROLE_ADMIN')")
@RequestMapping("/messages")
public class MessageController {

    private final UserService userService;
    private final MessagesService messagesService;

    public MessageController(UserService userService, MessagesService messagesService) {
        this.userService = userService;
        this.messagesService = messagesService;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByPhoneNumber(userDetails.getUser().getPhoneNumber()).get();
    }

    @GetMapping("")
    public String toMessages(Model model) {
        User authorizedUser = getCurrentUser();
        List<Message> receiverList = messagesService.findBySender(authorizedUser);
        List<Message> senderList = messagesService.findByReceiver(authorizedUser);
        Set<Integer> companion = new HashSet<>();

        for (Message message : receiverList) {
            companion.add(message.getReceiver().getId());
        }
        for (Message message : senderList) {
            companion.add(message.getSender().getId());
        }

        List<User> companions = new ArrayList<>();
        List<Message> companionsLastMessage = new ArrayList<>();

        for (Integer id:companion) {
            User compan = userService.findById(id).get();
            companions.add(compan);
            companionsLastMessage.add(messagesService.findBySenderAndReceiverLastMessage(authorizedUser,compan));
        }

        model.addAttribute("authorizedUser",authorizedUser);
        model.addAttribute("companions",companions);
        model.addAttribute("companionsLastMessage",companionsLastMessage);

        return "/user/messages";
    }

    @GetMapping("/{id}")
    public String messageDialog(Model model, @PathVariable(name = "id")int id){
        User authorizedUser = getCurrentUser();
        User companion = userService.findById(id).get();
        List<Message> companionMessages = new ArrayList<>();
        companionMessages.addAll(messagesService.findBySenderAndReceiver(authorizedUser,companion));
        companionMessages.addAll(messagesService.findBySenderAndReceiver(companion, authorizedUser));
        Collections.sort(companionMessages, new Comparator<Message>() {
            public int compare(Message o1, Message o2) {
                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
            }
        });

        model.addAttribute("authorizedUser",authorizedUser);
        model.addAttribute("companion",companion);
        model.addAttribute("companionMessages",companionMessages);

        return "/user/dialog-window";
    }

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestParam(value = "sender_id", required = true) int sender_id,
                              @RequestParam(value = "receiver_id", required = true) int receiver_id,
                              @RequestParam(value = "message", required = true) String message){
        User sender = userService.findById(sender_id).get();
        User receiver = userService.findById(receiver_id).get();
        Message newMessage = new Message();
        newMessage.setMessage(message);
        newMessage.setReceiver(receiver);
        newMessage.setSender(sender);
        newMessage.setCreatedAt(LocalDateTime.now());
        messagesService.save(newMessage);

        return "redirect:/messages";
    }

}
