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

}
