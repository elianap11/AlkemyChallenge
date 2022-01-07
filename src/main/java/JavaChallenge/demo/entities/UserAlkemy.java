package JavaChallenge.demo.entities;

import JavaChallenge.demo.Enum.UserRole;
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
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato de email debe ser válido")
    @Column(nullable = false, unique = true)
    private String mail;

    @NotBlank(message = "La clave es obligatorio")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    private String image;

    private Boolean status;

    public UserAlkemy() {
    }

    public UserAlkemy(Integer id, String name, String mail, String password, UserRole userRole, String image, Boolean status) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.userRole = userRole;
        this.image = image;
        this.status = status;
    }
}