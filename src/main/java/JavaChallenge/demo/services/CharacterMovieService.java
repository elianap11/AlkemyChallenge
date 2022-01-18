package JavaChallenge.demo.services;

import JavaChallenge.demo.entities.CharacterMovie;
import JavaChallenge.demo.entities.Movie;
import JavaChallenge.demo.repositories.CharacterMovieRepository;
import JavaChallenge.demo.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterMovieService {

    @Autowired
    private CharacterMovieRepository characterMovieRepository;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;


    @Transactional
    public CharacterMovie createCharacterMovie(CharacterMovie characterMovie, MultipartFile photo) throws Exception{
        if (!photo.isEmpty()) {
            characterMovie.setImage(photoService.copyPhoto(photo));
        }

        characterMovie.setStatus(true);
        return characterMovieRepository.save(characterMovie);
    }

    @Transactional
    public void update(Integer id, String name, Integer age, Double weight, String biography, List<Movie> movie, String image) {
        CharacterMovie characterMovie = characterMovieRepository.findById(id).get();
        characterMovieRepository.save(characterMovie);
    }

    @Transactional
    public void delete(Integer id) {
        characterMovieRepository.deleteById(id);
    }

    @Transactional
    public void enable(Integer id) {
        characterMovieRepository.enableCharacterMovie(id);
    }

    @Transactional
    public List<Object[]> showListCharacterMovie() {
        return characterMovieRepository.showListCharacterMovie();
    }

    @Transactional
    public List<CharacterMovie> findAll() {
        return characterMovieRepository.findAll();
    }

    @Transactional
    public List<CharacterMovie> findByName(String name) {
        return characterMovieRepository.findByName(name);
    }

    @Transactional
    public List<CharacterMovie> findByAge(Integer age) {
        return characterMovieRepository.findByAge(age);
    }

    @Transactional
    public Optional<CharacterMovie> findById(Integer id) {
        return characterMovieRepository.findById(id);
    }

    @Transactional
    public List<CharacterMovie> findAllByIdMovie(Integer idMovie) throws Exception {
        try {
            Movie movie = movieRepository.findById(idMovie).get();
            if (movie == null){
                throw new Exception("No se encontró una película con este id");
            }
            List<CharacterMovie> charactersListByMovie = movie.getCharacterMovieList();
            if (!charactersListByMovie.isEmpty()) {
                return charactersListByMovie;
            } else {
                throw new Exception("No se encontraron personajes relacionados a esta película");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}