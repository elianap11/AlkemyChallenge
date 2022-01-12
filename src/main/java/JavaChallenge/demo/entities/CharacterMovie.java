package JavaChallenge.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Entity
@Setter
@Getter
@SQLDelete(sql = "UPDATE character_movie SET status = false WHERE id = ?")

public class CharacterMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "La edad es obligatoria")
    @Column(nullable = false)
    @Positive(message = "La edad debe ser mayor a 0")
    private Integer age;

    @NotNull(message = "El peso es obligatorio")
    @Column(nullable = false)
    private Double weight;

    @NotBlank(message = "La historia es obligatoria")
    @Column(nullable = false)
    private String biography;

    //@NotEmpty(message = "La lista no puede estar vacía")
    @ManyToMany(mappedBy = "characterMovieList")
    @JsonIgnore
    private List<Movie> movieList;

    private String image;

    private Boolean status;

    public CharacterMovie() {
    }

    public CharacterMovie(Integer id, String name, Integer age, Double weight, String biography, List<Movie> movieList, String image, Boolean status) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.biography = biography;
        this.movieList = movieList;
        this.image = image;
        this.status = status;
    }
}
