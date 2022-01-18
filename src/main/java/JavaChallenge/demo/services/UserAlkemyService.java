package JavaChallenge.demo.services;

import JavaChallenge.demo.enums.UserRole;
import JavaChallenge.demo.entities.UserAlkemy;
import JavaChallenge.demo.repositories.UserAlkemyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserAlkemyService implements UserDetailsService {

    @Autowired
    private UserAlkemyRepository userAlkemyRepository;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private final String message = "No existe un usuario registrado con el correo %s";

    @Transactional
    public UserAlkemy createUser(UserAlkemy userAlkemy, MultipartFile photo) throws Exception, IOException {

        userAlkemy.setPassword(encoder.encode(userAlkemy.getPassword()));

        if (userAlkemyRepository.findAll().isEmpty()) {
            userAlkemy.setUserRole(UserRole.ADMIN);
        } else {
            userAlkemy.setUserRole(UserRole.USER);
        }

        if (!photo.isEmpty()) {
            userAlkemy.setImage(photoService.copyPhoto(photo));
        }

        userAlkemy.setStatus(true);

        emailService.sendThread(userAlkemy.getMail());

        return userAlkemyRepository.save(userAlkemy);
    }

    @Transactional
    public void update(Integer id, String name, String mail, String password, String image, MultipartFile photo) throws Exception, IOException {
        UserAlkemy userAlkemy = userAlkemyRepository.findById(id).get();

        if (!photo.isEmpty()) {
            userAlkemy.setImage(photoService.copyPhoto(photo));
        }

        userAlkemyRepository.save(userAlkemy);
    }

    @Transactional(readOnly = true)
    public List<UserAlkemy> getUserAlkemy() {
        return userAlkemyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public UserAlkemy findById(Integer id) {
        return userAlkemyRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public UserAlkemy findByMail(String mail) {
        Optional<UserAlkemy> optionalUserAlkemy = userAlkemyRepository.findByMail(mail);
        return optionalUserAlkemy.orElse(null);
    }

    @Transactional
    public void enableUserAlkemy(Integer id) {
        userAlkemyRepository.enable(id);
    }

    @Transactional
    public void deleteUserAlkemy(Integer id) {
        userAlkemyRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        UserAlkemy userAlkemy = userAlkemyRepository.findByMail(mail)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(message, mail)));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + userAlkemy.getUserRole().name());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpSession session = attributes.getRequest().getSession(true);

        session.setAttribute("mail", userAlkemy.getMail());
        session.setAttribute("userRol", userAlkemy.getUserRole().name());

        return new User(userAlkemy.getMail(), userAlkemy.getPassword(), Collections.singletonList(authority));
    }
}