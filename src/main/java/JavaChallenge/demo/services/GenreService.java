package JavaChallenge.demo.services;

import JavaChallenge.demo.entities.Movie;
import JavaChallenge.demo.entities.Genre;
import JavaChallenge.demo.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public void createGenre(String name, List<Movie> movie, String image){

        Genre genre = new Genre();

        genre.setName(name);
        genre.setMovie(movie);
        genre.setImage(image);
        genre.setStatus(true);

        genreRepository.save(genre);
    }

    @Transactional
    public void update(Integer id, String name, List<Movie> movie, String image) {
        Genre genre = genreRepository.findById(id).get();
        genreRepository.save(genre);
    }

    @Transactional
    public void delete(Integer id){
        genreRepository.deleteById(id);
    }

    @Transactional
    public void enable(Integer id){
        genreRepository.enable(id);
    }

    @Transactional
    public void showGenre(){
        genreRepository.findAll();
    }

}
