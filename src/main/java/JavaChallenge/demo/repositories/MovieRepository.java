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

    @Modifying
    @Query("UPDATE Movie f SET f.status = true WHERE f.idMovie = :idMovie")
    void enableMovie(@Param("idMovie") Integer idMovie);

    @Query(value = "SELECT * FROM movie WHERE title LIKE '%' ? '%'", nativeQuery = true)
    List<Movie> findByTitle(String title);

    @Query(value = "SELECT * FROM movie WHERE genre_id = ?1", nativeQuery = true)
    List<Movie> filterByGenre(Integer genre);

    @Query("SELECT title, image, creationDate FROM Movie")
    List<Object[]> showFilterMovies();
}
