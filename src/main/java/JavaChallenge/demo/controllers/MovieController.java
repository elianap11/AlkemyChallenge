package JavaChallenge.demo.controllers;

import JavaChallenge.demo.entities.Movie;
import JavaChallenge.demo.services.MovieService;
import JavaChallenge.demo.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private PhotoService photoService;

    @GetMapping
    public List<Object[]> showMovie() {
        return movieService.showFilterMovies();
    }

    @GetMapping("/all")
    public List<Movie> getMovie() {
        return movieService.findAll();
    }

    @GetMapping("/{idMovie}")
    public Optional<Movie> getById(@PathVariable("idMovie") Integer idMovie) {
        return movieService.findById(idMovie);
    }

    @GetMapping(params = "title")
    public List<Movie> getByTitle(@RequestParam("title") String title) {
        return movieService.findByTitle(title);
    }

    @GetMapping(value = "", params = "genre")
    public List<Movie> getByGenre(@RequestParam("genre") Integer genre) {
        return movieService.filterByGenre(genre);
    }

    @GetMapping(params = "order")
    public List<Movie> getByOrder(@RequestParam("order") String order){
        return movieService.filterByOrder(order);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public Movie saveMovie(@Valid @ModelAttribute Movie movie, BindingResult result, @RequestParam (value = "image") MultipartFile photo) throws Exception{
        if (!photo.isEmpty()) {
            movie.setImage(photoService.copyPhoto(photo));
        }
       return movieService.createMovie(movie);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "delete/{idMovie}")
    public String deleteMovie(@PathVariable("idMovie") Integer idMovie){
        try {
            movieService.delete(idMovie);
            return "La película "+idMovie+" fue eliminada";
        } catch (Exception e) {
            return "La película "+idMovie+" no existe";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "enable/{idMovie}")
    public String enableMovie(@PathVariable("idMovie") Integer idMovie) {
        try {
            movieService.enable(idMovie);
            return "El personaje " + idMovie + " fue habilitado";
        } catch (Exception e) {
            return "El personaje " + idMovie + " no existe";
        }
    }
}