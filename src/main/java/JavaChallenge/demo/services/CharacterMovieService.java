package JavaChallenge.demo.services;

import JavaChallenge.demo.entities.CharacterMovie;
import JavaChallenge.demo.entities.Movie;
import JavaChallenge.demo.repositories.CharacterMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterMovieService {

    @Autowired
    private CharacterMovieRepository characterMovieRepository;

    @Transactional
    public void createCharacterMovie(String name, Integer age, Double weight, String biography, List<Movie> movie, String image, Boolean status) {
        CharacterMovie characterMovie = new CharacterMovie();

        characterMovie.setName(name);
        characterMovie.setAge(age);
        characterMovie.setWeight(weight);
        characterMovie.setBiography(biography);
        characterMovie.setMovie(movie);
        characterMovie.setImage(image);
        characterMovie.setStatus(true);

        characterMovieRepository.save(characterMovie);
    }

    @Transactional
    public void update(Integer id, String name, Integer age, Double weight, String biography, List<Movie> movie, String image) {
       CharacterMovie characterMovie = characterMovieRepository.findById(id).get();
       characterMovieRepository.save(characterMovie);
    }

    @Transactional
    public void delete(Integer id){
        characterMovieRepository.deleteById(id);
    }

    @Transactional
    public void enable(Integer id){
        characterMovieRepository.enable(id);
    }

    @Transactional
    public List<CharacterMovie> findAll() {
        return characterMovieRepository.findAll();
    }

    @Transactional
    public List<CharacterMovie> findByName(String name){
        return characterMovieRepository.findByName(name);
    }

    @Transactional
    public List<CharacterMovie> findByAge(Integer age){
        return characterMovieRepository.findByAge(age);
    }

    @Transactional
    public Optional<CharacterMovie> findById(Integer id){
        return characterMovieRepository.findById(id);
    };

    @Transactional
    public List<CharacterMovie> findByMovies(String movie){
        return characterMovieRepository.findByMovies(movie);
    }
}
