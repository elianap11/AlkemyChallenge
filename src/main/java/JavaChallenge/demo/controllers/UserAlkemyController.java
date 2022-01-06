package JavaChallenge.demo.controllers;

import JavaChallenge.demo.entities.UserAlkemy;
import JavaChallenge.demo.services.UserAlkemyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
public class UserAlkemyController {

    @Autowired
    private UserAlkemyService userAlkemyService;

    private UserAlkemy userAlkemy;

    @PostMapping("/register")
    public void createUser(String name, String mail, String password, String image, MultipartFile photo) throws Exception{
        userAlkemyService.createUser(name, mail, password, image, photo);
    }

    @PostMapping("/login")
    public UserAlkemy loginUser(String name, String mail, String password, String image, MultipartFile photo) {
        try {
            if(userAlkemy != null){
                userAlkemyService.loadUserByUsername(userAlkemy.getName());
                return userAlkemy;
            }else{
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}