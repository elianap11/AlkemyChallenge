package JavaChallenge.demo.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE movie SET status = false WHERE id = ?")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMovie;

    @NotBlank(message = "El título es obligatorio")
    @Column(nullable = false)
    private String title;

    //@NotBlank(message = "La fecha es obligatoria")
    @Column(nullable = false)
    private LocalDate creationDate;

    @NotNull(message = "La calificación es obligatoria")
    @Column
    @Min(value = 1, message = "El valor mínimo es 1")
    @Max(value = 5, message = "El valor máximo es 5")
    private Integer rate;

    @ManyToMany
    @JoinTable(name = "rel_movie_character", joinColumns = @JoinColumn (name = "id_movie", nullable = false), inverseJoinColumns = @JoinColumn (name = "id_character", nullable = false))
    //@NotEmpty(message = "La lista no puede estar vacía")
    private List<CharacterMovie> characterMovieList;

    @ManyToOne
    private Genre genre;

    private String image;

    private Boolean status;

    public Movie() {
    }

    public Movie(Integer idMovie, String title, LocalDate creationDate, Integer rate, List<CharacterMovie> characterMovieList, Genre genre, String image, Boolean status) {
        this.idMovie = idMovie;
        this.title = title;
        this.creationDate = creationDate;
        this.rate = rate;
        this.characterMovieList = characterMovieList;
        this.genre = genre;
        this.image = image;
        this.status = status;
    }
}
