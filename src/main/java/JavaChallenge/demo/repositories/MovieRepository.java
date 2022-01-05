package JavaChallenge.demo.repositories;

import JavaChallenge.demo.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    /*@Modifying
    @Query("UPDATE Movie f SET f.title = :title, f.creationDate = :creationDate, f.rate = :rate, f.characterMovies = :characterMovies, f.genre = :genre, f.image = :image WHERE f.id = :id")
    void update(@Param("id") Integer id, @Param("title") String title, @Param("creationDate") LocalDate creationDate, @Param("characterMovies") List<CharacterMovie> characterMovies, @Param("rate") Integer rate, @Param("genre") Genre genre, @Param("image") String image);*/

    @Modifying
    @Query("UPDATE Movie f SET f.status = true WHERE f.id = :id")
    void enable(@Param("id") Integer id);

    @Query(value = "SELECT * FROM movie WHERE title LIKE '%' ? '%'", nativeQuery = true)
    List<Movie> findByTitle(String title);

    //BUSCAR POR GÉNERO

    @Query(value= "SELECT * FROM movie m INNER JOIN genre g ON  m.genre_id = g.id WHERE g.name LIKE '%' ? '%'", nativeQuery = true)
    List<Movie> filterByGenre(String genre);


    //Mostrar sólo 3 atributos
    @Query(value = "SELECT title, image, creationDate FROM movie", nativeQuery = true)
    //public Iterable<Object[]> showFilterMovies();
    List<Movie> showFilterMovies();

    /*
    //Mostramos fecha ASC
    @Query(value = "SELECT title, image, creationDate FROM movie ORDER BY creationDate ASC", nativeQuery = true)
    public Iterable <Object[]> orderByASC();

    //Mostramos fecha DESC
    @Query(value = "SELECT title, image, creationDate FROM movie ORDER BY creationDate DESC", nativeQuery = true)
    public Iterable<Object[]> orderByDESC();*/

}
