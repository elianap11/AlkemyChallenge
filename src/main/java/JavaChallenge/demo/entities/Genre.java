package JavaChallenge.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@SQLDelete(sql = "UPDATE genre SET status = false WHERE id = ?")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy="genre")
    @JsonIgnore
    private List<Movie> movie;

    private String image;

    private Boolean status;

    public Genre() {
    }

    public Genre(Integer id, String name, List<Movie> movie, String image, Boolean status) {
        this.id = id;
        this.name = name;
        this.movie = movie;
        this.image = image;
        this.status = status;
    }
}
