package JavaChallenge.demo.repositories;

import JavaChallenge.demo.entities.CharacterMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterMovieRepository extends JpaRepository<CharacterMovie, Integer> {

    @Query(value = "SELECT image, name FROM CharacterMovie", nativeQuery = true)
    List<CharacterMovie> showListCharacterMovie();

    @Modifying
    @Query("UPDATE CharacterMovie c SET c.status = true WHERE c.id = :id")
    void enable(@Param("id") Integer id);

    @Query(value = "SELECT * FROM CharacterMovie WHERE name LIKE '%' ? '%'", nativeQuery = true)
    List<CharacterMovie> findByName(String name);

    @Query(value = "SELECT * FROM CharacterMovie WHERE age = ?", nativeQuery = true)
    List<CharacterMovie> findByAge(Integer age);

    @Query(value = "SELECT c.name, m.title FROM CharacterMovie c\n" +
            "    LEFT JOIN rel_movie_character r\n" +
            "    ON c.id = r.id_character\n" +
            "    RIGHT JOIN movie m\n" +
            "    ON  m.id = r.id_movie\n" +
            "    WHERE m.title LIKE '%' ? '%'", nativeQuery = true)
    List<CharacterMovie> findByMovies(String movie);

}
