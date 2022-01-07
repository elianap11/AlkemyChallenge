package JavaChallenge.demo.controllers;

import JavaChallenge.demo.entities.CharacterMovie;
import JavaChallenge.demo.entities.Movie;
import JavaChallenge.demo.services.MovieService;
import JavaChallenge.demo.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private PhotoService photoService;

    //Por filtro
    @GetMapping
    public List<Object[]> showFilterMovies() {
        return movieService.showFilterMovies();
    }

    @GetMapping("/all")
    public List<Movie> getAll() {
        return movieService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Movie> getById(@PathVariable("id") Integer id) {
        return movieService.findById(id);
    }

    @GetMapping(params = "title")
    public List<Movie> getByTitle(@RequestParam("title") String title) {
        return movieService.findByTitle(title);
    }

    @GetMapping(value = "", params = "genre")
    public List<Movie> getByGenre(@RequestParam("genre") Integer genre) {
        return movieService.filterByGenre(genre);
    }

    //Falta por orden
    @GetMapping(params = "order")
    public List<Movie> getByOrder(@RequestParam("order") String order){
        return movieService.filterByOrder(order);
    }



    @DeleteMapping(path = "delete/{id}")
    public String deleteMovie(@PathVariable("id") Integer id){
        try {
            movieService.delete(id);
            return "La película "+id+" fue eliminada";
        } catch (Exception e) {
            return "La película "+id+" no existe";
        }
    }



    @PostMapping("/save")
    public Movie saveMovie(@RequestBody Movie movie){
       movie.setImage("");
       return movieService.createMovie(movie);
    }

    @GetMapping(path = "enable/{id}")
    public String enable(@PathVariable("id") Integer id) {
        try {
            movieService.enable(id);
            return "El personaje " + id + " fue habilitado";
        } catch (Exception e) {
            return "El personaje " + id + " no existe";
        }
    }

   /* @PostMapping("/save")
    public Movie saveMovie(@RequestParam("image") MultipartFile photo, @ModelAttribute Movie movie) throws Exception{
        if (!photo.isEmpty()) {
            movie.setImage(photoService.copyPhoto(photo);
        }
        movieService.createMovie(movie);
        return new RedirectView("/movies");
    }
        return movieService.createMovie(movie);

    }*/
}

