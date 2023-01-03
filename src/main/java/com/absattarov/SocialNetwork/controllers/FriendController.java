package com.absattarov.SocialNetwork.controllers;

import com.absattarov.SocialNetwork.models.Friend;
import com.absattarov.SocialNetwork.models.RequestFriendship;
import com.absattarov.SocialNetwork.models.Subscriber;
import com.absattarov.SocialNetwork.models.User;
import com.absattarov.SocialNetwork.security.UserDetails;
import com.absattarov.SocialNetwork.services.FriendsService;
import com.absattarov.SocialNetwork.services.RequestFriendshipService;
import com.absattarov.SocialNetwork.services.SubscribersService;
import com.absattarov.SocialNetwork.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MODER','ROLE_ADMIN')")
@RequestMapping("/friends")
public class FriendController {

    private final UserService userService;
    private final RequestFriendshipService requestFriendshipService;
    private final FriendsService friendsService;

    private final SubscribersService subscribersService;

    public FriendController(UserService userService, RequestFriendshipService requestFriendshipService, FriendsService friendsService, SubscribersService subscribersService) {
        this.userService = userService;
        this.requestFriendshipService = requestFriendshipService;
        this.friendsService = friendsService;
        this.subscribersService = subscribersService;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByPhoneNumber(userDetails.getUser().getPhoneNumber()).get();
    }

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toFriendsPage(Model model, @RequestParam(value = "q", defaultValue = "") String searchLine) {

        User currentUser = getCurrentUser();

        List<User> friends = new ArrayList<>();
        List<User> searchResult = new ArrayList<>();

        if (searchLine.isEmpty()) {
            friends = currentUser.getFriends();
        } else {
            String[] splited = searchLine.split(" ");
            if (splited.length == 1) {
                searchResult = userService.findBySearchLine(splited[0], "");
            } else if (splited.length == 2) {
                searchResult = userService.findBySearchLine(splited[0], splited[1]);
            }
            searchResult.remove(currentUser);

        }

        ArrayList<Integer> friendsId = new ArrayList<>();
        for (User user : currentUser.getFriends()) {
            friendsId.add(user.getId());
        }

        List<User> receiverList = requestFriendshipService.findBySender(currentUser);

        ArrayList<Integer> receiversId = new ArrayList<>();
        for (User user : receiverList) {
            receiversId.add(user.getId());
        }

        model.addAttribute("friends", friends);
        model.addAttribute("searchResult", searchResult);
        model.addAttribute("receiversId", receiversId);
        model.addAttribute("friendsId", friendsId);
        model.addAttribute("authorizedUser", currentUser);

        return "/user/friends";
    }

    @PostMapping("/request-friendship")
    public String sendRequest(@RequestParam(value = "sender_id", required = true) int sender_id,
                              @RequestParam(value = "receiver_id", required = true) int receiver_id) {

        RequestFriendship requestFriendship = new RequestFriendship();
        requestFriendship.setSender(userService.findById(sender_id).get());
        requestFriendship.setReceiver(userService.findById(receiver_id).get());
        requestFriendshipService.save(requestFriendship);

        return "redirect:/friends";
    }

    @GetMapping("/requests")
    public String friendshipRequests(Model model) {
        User currentUser = getCurrentUser();

        List<User> senders = new ArrayList<>();

        senders = requestFriendshipService.findByReceiver(currentUser);


        model.addAttribute("authorizedUser", currentUser);
        model.addAttribute("senders", senders);

        return "/user/friendship-request";
    }

    @PostMapping("/accept-friendship")
    public String acceptFriendship(@RequestParam(value = "user_id", required = true) int user_id,
                                   @RequestParam(value = "sender_id", required = true) int sender_id){
        User receiver = userService.findById(user_id).get();
        User sender = userService.findById(sender_id).get();

        requestFriendshipService.deleteBySenderAndReceiver(sender,receiver);

        Friend friendMutual1 = new Friend();
        Friend friendMutual2 = new Friend();

        friendMutual1.setUser(receiver);
        friendMutual1.setFriend(sender);

        friendMutual2.setFriend(receiver);
        friendMutual2.setUser(sender);

        friendsService.save(friendMutual1);
        friendsService.save(friendMutual2);

        Subscriber subscriberMutual1 = new Subscriber();
        Subscriber subscriberMutual2 = new Subscriber();

        subscriberMutual1.setUser(receiver);
        subscriberMutual1.setSubscriber(sender);

        subscriberMutual2.setSubscriber(receiver);
        subscriberMutual2.setUser(sender);

        subscribersService.save(subscriberMutual1);
        subscribersService.save(subscriberMutual2);

        return "redirect:/friends/requests";
    }

    @PostMapping("/decline-friendship")
    public String declineFriendship(@RequestParam(value = "user_id", required = true) int user_id,
                                   @RequestParam(value = "sender_id", required = true) int sender_id){
        User receiver = userService.findById(user_id).get();
        User sender = userService.findById(sender_id).get();

        requestFriendshipService.deleteBySenderAndReceiver(sender,receiver);

        return "redirect:/friends/requests";
    }
}
