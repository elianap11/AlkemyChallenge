package JavaChallenge.demo.services;

import JavaChallenge.demo.entities.Movie;
import JavaChallenge.demo.entities.Genre;
import JavaChallenge.demo.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private PhotoService photoService;

    public Genre createGenre(Genre genre){
        genre.setStatus(true);
        return genreRepository.save(genre);
    }

    @Transactional
    public void update(Integer id, String name, List<Movie> movie, String image) {
        Genre genre = genreRepository.findById(id).get();
        genreRepository.save(genre);
    }

    @Transactional
    public Optional<Genre> findById(Integer id) {
        return genreRepository.findById(id);
    }

    @Transactional
    public void delete(Integer id) {
        genreRepository.deleteById(id);
    }

    @Transactional
    public void enable(Integer id) {
        genreRepository.enableGenre(id);
    }

    @Transactional
    public List<Genre> showGenre() {
        return genreRepository.findAll();
    }

}