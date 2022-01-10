package JavaChallenge.demo.controllers;

import JavaChallenge.demo.entities.UserAlkemy;
import JavaChallenge.demo.services.UserAlkemyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserAlkemyController {

    @Autowired
    private UserAlkemyService userAlkemyService;

    private UserAlkemy userAlkemy;

    @PostMapping("/register")
    public void createUser(@Valid @ModelAttribute UserAlkemy userAlkemy, BindingResult result, @RequestParam (value = "image") MultipartFile photo) throws Exception {
        userAlkemyService.createUser(userAlkemy, photo);
    }

    @PostMapping("/login")
    public UserAlkemy loginUser(@Valid @ModelAttribute UserAlkemy userAlkemy, BindingResult result) {
        try {
            if(userAlkemy != null){
                UserDetails user = userAlkemyService.loadUserByUsername(userAlkemy.getMail());
                if (user != null){
                    return userAlkemyService.findByMail(userAlkemy.getMail());
                }else{
                    return null;
                }
            }else{
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/update")
    public void save (@Valid @ModelAttribute UserAlkemy userAlkemy, BindingResult result, @RequestParam("uploadFile") MultipartFile photo) throws Exception, IOException {
       if (!photo.isEmpty()) {
            userAlkemy.setImage(userAlkemy.getImage());
       }
        userAlkemyService.createUser(userAlkemy, photo);
    }

    @GetMapping("/all")
    public List<UserAlkemy> getAll() {
        return userAlkemyService.getUser();
    }

    @DeleteMapping(path = "delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        try {
            userAlkemyService.delete(id);
            return "El usuario " +id+ " fue eliminado";
        } catch (Exception e) {
            return "El usuario "+ id+ " no existe";
        }
    }

    @GetMapping(path = "enable/{id}")
    public String enable(@PathVariable("id") Integer id) {
        try {
            userAlkemyService.enable(id);
            return "El usuario " + id + " fue habilitado";
        } catch (Exception e) {
            return "El usuario " + id + " no existe";
        }
    }
}
