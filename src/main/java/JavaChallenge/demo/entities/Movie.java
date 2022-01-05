package JavaChallenge.demo.entities;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE movie SET status = false WHERE id = ?")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El título es obligatorio")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "La fecha es obligatoria")
    @Column(nullable = false)
    private LocalDate creationDate;

    @NotBlank(message = "La calificación es obligatoria")
    @Column(nullable = false)
    private Integer rate;

    @ManyToMany
    @JoinTable(name = "rel_movie_character", joinColumns = @JoinColumn (name = "id_character", nullable = false), inverseJoinColumns = @JoinColumn (name = "id_movie", nullable = false))
    @NotEmpty(message = "La lista no puede estar vacía")
    private List<CharacterMovie> characterMovieList;

    @ManyToOne
    private Genre genre;

    private String image;

    private Boolean status;

    public Movie() {
    }

    public Movie(Integer id, String title, LocalDate creationDate, Integer rate, List<CharacterMovie> characterMovieList, Genre genre, String image, Boolean status) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
        this.rate = rate;
        this.characterMovieList = characterMovieList;
        this.genre = genre;
        this.image = image;
        this.status = status;
    }
}
