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

    //Object[]
    @Query("SELECT image, name FROM CharacterMovie")
    List<Object[]> showListCharacterMovie();

    @Modifying
    @Query("UPDATE CharacterMovie c SET c.status = true WHERE c.id = :id")
    void enable(@Param("id") Integer id);

    List<CharacterMovie> findByName(String name);

    List<CharacterMovie> findByAge(Integer age);

   @Query(value = "SELECT c.name, m.title FROM character_movie c INNER JOIN rel_movie_character r ON c.id = r.id_character INNER JOIN movie m ON  m.id = r.id_movie WHERE m.id = ?1", nativeQuery = true)
    List<CharacterMovie> findByMovie(Integer id);

}
