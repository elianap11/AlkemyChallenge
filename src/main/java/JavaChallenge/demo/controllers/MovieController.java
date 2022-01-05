package JavaChallenge.demo.controllers;

import JavaChallenge.demo.entities.Movie;
import JavaChallenge.demo.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<Movie> getAll() {
        return movieService.findAll();
    }

 /*   //Por filtro
    @GetMapping
    public List<Movie> showFilterMovies() {
        return movieService.showFilterMovies();
    }*/

   @GetMapping("/{id}")
    public Optional<Movie> getById(@PathVariable("id") Integer id) {
        return movieService.findById(id);
    }

    @GetMapping(params = "title")
    public List<Movie> getByTitle(@RequestParam("title") String title) {
        return movieService.findByTitle(title);
    }

    @GetMapping(value = "", params = "genre")
    public List<Movie> getByGenre(@RequestParam("genre") String genre) {
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

}

