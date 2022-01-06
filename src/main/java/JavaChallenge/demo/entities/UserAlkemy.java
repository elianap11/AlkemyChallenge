package JavaChallenge.demo.entities;

import JavaChallenge.demo.Enum.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Setter
@Getter

@SQLDelete(sql = "UPDATE UserAlkemy SET status = false WHERE id = ?")
public class UserAlkemy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String mail;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    private String image;

    private Boolean status;
}
