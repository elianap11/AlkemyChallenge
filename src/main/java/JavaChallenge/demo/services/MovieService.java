package JavaChallenge.demo.services;

import JavaChallenge.demo.entities.CharacterMovie;
import JavaChallenge.demo.entities.Movie;
import JavaChallenge.demo.entities.Genre;
import JavaChallenge.demo.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PhotoService photoService;

    @Transactional
    public Movie createMovie(Movie movie) throws Exception {
        movie.setStatus(true);
        return movieRepository.save(movie);
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
    public Optional<Movie> findById(Integer idMovie){
        return movieRepository.findById(idMovie);
    }

    @Transactional
    public void delete(Integer id){
        movieRepository.deleteById(id);
    }

    @Transactional
    public void enable(Integer id){
        movieRepository.enableMovie(id);
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