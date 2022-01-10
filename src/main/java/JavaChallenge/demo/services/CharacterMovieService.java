package JavaChallenge.demo.services;

import JavaChallenge.demo.entities.CharacterMovie;
import JavaChallenge.demo.entities.Movie;
import JavaChallenge.demo.repositories.CharacterMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.util.List;
import java.util.Optional;

@Service
public class CharacterMovieService {

    @Autowired
    private CharacterMovieRepository characterMovieRepository;

    @Autowired
    private PhotoService photoService;


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
        characterMovieRepository.enable(id);
    }

    //Object[]
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
    public List<CharacterMovie> findByMovie(Integer id) {
        return characterMovieRepository.findByMovie(id);
    }

    @Transactional
    public Optional<CharacterMovie> findById(Integer id) {
        return characterMovieRepository.findById(id);
    }

    ;

}
