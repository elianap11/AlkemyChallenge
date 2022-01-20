package JavaChallenge.demo.controllers;

import JavaChallenge.demo.entities.Movie;
import JavaChallenge.demo.entities.UserAlkemy;
import JavaChallenge.demo.services.UserAlkemyService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class UserAlkemyController {

    @Autowired
    private UserAlkemyService userAlkemyService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @ModelAttribute UserAlkemy userAlkemy, BindingResult result) throws Exception {
        try {
            if (userAlkemy != null) {
                UserDetails userDetails = userAlkemyService.loadUserByUsername(userAlkemy.getMail());
                if (userDetails == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Correo no encontrado.");
                }
                if (userAlkemyService.verifyPassword(userAlkemy.getPassword(), userDetails.getPassword())) {

                    String token = getJWTToken(userAlkemy.getMail());
                    userAlkemy.setToken(token);
                    return ResponseEntity.status(HttpStatus.OK).body("Mail: " + userAlkemy.getMail() + "\nToken: " + userAlkemy.getToken());
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contraseña incorrecta.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUserAlkemy(@Valid @ModelAttribute UserAlkemy userAlkemy, BindingResult result, @RequestParam(value = "image") MultipartFile photo) {
        try {
            List<UserAlkemy> usersAlkemy = userAlkemyService.getUserAlkemy();
            for (UserAlkemy u : usersAlkemy) {
                if (u.getMail().equals(userAlkemy.getMail())) {
                    return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Ya se encuentra registrado un usuario con ese correo");
                }
            }

            UserAlkemy userAlkemyToken = userAlkemyService.createUser(userAlkemy, photo);

            String token = getJWTToken(userAlkemy.getMail());
            userAlkemyToken.setToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(userAlkemyToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
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
                .setExpiration(new Date((long) (System.currentTimeMillis() + 172800000)))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

    @GetMapping("/{id}")
    public Optional<UserAlkemy> getById(@PathVariable("id") Integer id) {
        return Optional.ofNullable(userAlkemyService.findById(id));
    }

    @PostMapping("/update")
    public void saveUserAlkemy(@Valid @ModelAttribute UserAlkemy userAlkemy, BindingResult result, @RequestParam(value = "image") MultipartFile photo) throws Exception, IOException {
           userAlkemyService.createUser(userAlkemy, photo);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<UserAlkemy> getUserAlkemy() {
        return userAlkemyService.getUserAlkemy();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "delete/{id}")
    public String deleteUserAlkemy(@PathVariable("id") Integer id) {
        try {
            userAlkemyService.deleteUserAlkemy(id);
            return "El usuario " + id + " fue eliminado";
        } catch (Exception e) {
            return "El usuario " + id + " no existe";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "enable/{id}")
    public String enableUserAlkemy(@PathVariable("id") Integer id) {
        try {
            userAlkemyService.enableUserAlkemy(id);
            return "El usuario " + id + " fue habilitado";
        } catch (Exception e) {
            return "El usuario " + id + " no existe";
        }
    }
}