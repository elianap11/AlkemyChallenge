package JavaChallenge.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Setter
@Getter
@SQLDelete(sql = "UPDATE genre SET status = false WHERE id = ?")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @OneToMany(mappedBy="genre")
    @JsonIgnore
    private List<Movie> movieList;

    private String image;

    private Boolean status;

    public Genre() {
    }

    public Genre(Integer id, String name, List<Movie> movieList, String image, Boolean status) {
        this.id = id;
        this.name = name;
        this.movieList = movieList;
        this.image = image;
        this.status = status;
    }
}
