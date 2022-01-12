package JavaChallenge.demo.controllers;

import JavaChallenge.demo.Security.User;
import JavaChallenge.demo.entities.UserAlkemy;
import JavaChallenge.demo.services.UserAlkemyService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class UserAlkemyController {

    @Autowired
    private UserAlkemyService userAlkemyService;

    private UserAlkemy userAlkemy;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("mail") String username, @RequestParam("password") String psw) {
        try {
            UserAlkemy usuario = userAlkemyService.findByUserAndPsw(username, psw);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. No se encontró un usuario registrado con ese nombre o contraseña .\"}");
            }
            String token = getJWTToken(username);
            User u = new User();
            u.setUser(username);
            u.setToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(u);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. " + e.getMessage() + ".\"}");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @ModelAttribute UserAlkemy userAlkemy, BindingResult result, @RequestParam(value = "image") MultipartFile photo) {
        try {
            List<UserAlkemy> usuarios = userAlkemyService.getUser();
            for (UserAlkemy u : usuarios) {
                if (u.getMail().equals(userAlkemy.getMail())) {
                    return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Ya se encuentra registrado un usuario con ese nombre");
                }
            }
            UserAlkemy usuario = userAlkemyService.createUser(userAlkemy, photo);
            String token = getJWTToken(userAlkemy.getMail());
            User u = new User();
            u.setUser(usuario.getMail());
            u.setPsw(usuario.getPassword());
            u.setToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(u);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. " + e.getMessage() + ".\"}");
        }
    }


    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("alkemyJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }


    /* @PostMapping("/register")
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
*/
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
