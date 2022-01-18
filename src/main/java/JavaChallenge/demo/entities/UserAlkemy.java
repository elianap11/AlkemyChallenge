package JavaChallenge.demo.entities;

import JavaChallenge.demo.enums.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Setter
@Getter
@SQLDelete(sql = "UPDATE UserAlkemy SET status = false WHERE id = ?")
public class UserAlkemy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "El formato de email debe ser válido")
    @Column(unique = true)
    private String mail;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Column
    private String image;

    @Column
    private Boolean status;

    @Column
    private String token;

    public UserAlkemy() {
    }

    public UserAlkemy(Integer id, String name, String mail, String password, UserRole userRole, String image, Boolean status, String token) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.userRole = userRole;
        this.image = image;
        this.status = status;
        this.token = token;
    }
}