package com.absattarov.SocialNetwork.controllers;

import com.absattarov.SocialNetwork.models.User;
import com.absattarov.SocialNetwork.models.UserPhoto;
import com.absattarov.SocialNetwork.services.RegistrationService;
import com.absattarov.SocialNetwork.services.UserPhotoService;
import com.absattarov.SocialNetwork.services.UserService;
import com.absattarov.SocialNetwork.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserValidator userValidator;
    private final UserService userService;
    private final UserPhotoService userPhotoService;
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(UserValidator userValidator, UserService userService, UserPhotoService userPhotoService, RegistrationService registrationService) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.userPhotoService = userPhotoService;
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user){
        return "auth/registration";
    }

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\target\\classes\\static\\photos";
    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) throws IOException {
        userValidator.validate(user,bindingResult);
        if(bindingResult.hasErrors()){
            return "/auth/registration";
        }
        int saveUserId = registrationService.register(user);

        ///

        int UserId = userService.findById(saveUserId).get().hashCode();
        String directoryName = "user_"+UserId;
        Path path = Paths.get(UPLOAD_DIRECTORY,directoryName);

        if(!Files.exists(path)){
            new File(path.toString()).mkdirs();
        }

        if(Files.exists(path)){
            String photoName = "default";

            Path fileNameAndPath = Paths.get(path.toString(), photoName+".png");

            String fileNameAndPathToString = fileNameAndPath.toString().replace("\\","/");
            String cuttedPath = fileNameAndPathToString.substring(fileNameAndPathToString.lastIndexOf("/target"));

            writePhoto(cuttedPath.substring(1));
            cuttedPath = cuttedPath.substring(cuttedPath.lastIndexOf("/photos"));

            UserPhoto userPhoto = new UserPhoto();
            userPhoto.setUser(userService.findById(saveUserId).get());

            userPhoto.setPhotoPath(cuttedPath);
            userPhoto.setCreatedAt(LocalDateTime.now());
            userPhoto.setRating(0);
            userPhotoService.save(userPhoto);
            ///
            User user1 = userService.findById(saveUserId).get();
            user1.setAvatar(cuttedPath);
            userService.update(user1);
        }
        else {
            System.out.println("Upload fails");
        }

        ///



        return "redirect:/auth/login";
    }

    private void writePhoto(String path) throws IOException{
        File copied = new File(path);
        try (
                InputStream in = new BufferedInputStream(
                        new FileInputStream("src/main/resources/static/img/default-avatar.png"));
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
}
