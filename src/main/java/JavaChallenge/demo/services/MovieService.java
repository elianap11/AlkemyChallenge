package JavaChallenge.demo.services;

import JavaChallenge.demo.entities.CharacterMovie;
import JavaChallenge.demo.entities.Movie;
import JavaChallenge.demo.entities.Genre;
import JavaChallenge.demo.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public Movie createMovie(Movie movie) {
        movie.setStatus(true);
        movieRepository.save(movie);
        return movie;
    }

    @Transactional
    public void update(Integer id, String title, LocalDate creationDate, Integer rate, List<CharacterMovie> characterMovies, Genre genre, String image) {
        Movie movie = movieRepository.findById(id).get();
        movieRepository.save(movie);
    }

    @Transactional
    public List<Movie> findAll(){
        return movieRepository.findAll();
    }

    @Transactional
    public Optional<Movie> findById(Integer id){
        return movieRepository.findById(id);
    }

    @Transactional
    public void delete(Integer id){
        movieRepository.deleteById(id);
    }

    @Transactional
    public void enable(Integer id){
        movieRepository.enable(id);
    }

    @Transactional
    public List<Movie> findByTitle(String title){
        return movieRepository.findByTitle(title);
    }

    @Transactional
    public List<Movie> filterByGenre(Integer genre){
        return movieRepository.filterByGenre(genre);
    }

    @Transactional
    public List<Object[]> showFilterMovies(){
        return movieRepository.showFilterMovies();
    }


    @Transactional
    public List<Movie> filterByOrder(String order) {
        if (order.equals("ASC")) {
            return movieRepository.findAll(Sort.by(Sort.Direction.ASC, "creationDate"));
        } else if (order.equals("DESC")) {
            return movieRepository.findAll(Sort.by(Sort.Direction.DESC, "creationDate"));
        }else{
            return movieRepository.findAll();
        }
    }




}
