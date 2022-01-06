package JavaChallenge.demo.repositories;

import JavaChallenge.demo.entities.UserAlkemy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAlkemyRepository extends JpaRepository<UserAlkemy, Integer> {

    Optional<UserAlkemy> findByMail(String mail);

    // Creación de consulta a partir del nombre de método
    boolean existsByMail(String mail);

    @Modifying
    @Query("UPDATE UserAlkemy u SET u.status = true WHERE u.id = :id")
    void enable(@Param("id") Integer id);

}
