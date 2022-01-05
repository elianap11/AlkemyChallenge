package JavaChallenge.demo.controllers;

import JavaChallenge.demo.entities.CharacterMovie;
import JavaChallenge.demo.services.CharacterMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/characters")
public class CharacterMovieController {


    @Autowired
    private CharacterMovieService characterMovieService;


   /* @GetMapping
    public List<CharacterMovie> showListCharacterMovie() {
        return characterMovieService.showListCharacterMovie();
    }*/

    @GetMapping
    public List<CharacterMovie> findAll() {
        return characterMovieService.findAll();
    }

    @GetMapping(path = "/{id}")
    public Optional<CharacterMovie> findById(@PathVariable("id") Integer id) {
        return characterMovieService.findById(id);
    }

    @GetMapping(params = "name")
    public List<CharacterMovie> findByName(@RequestParam("name") String name) {
        return characterMovieService.findByName(name);
    }

    @GetMapping(params = "age")
    public List<CharacterMovie> findByAge(@RequestParam("age") Integer age) {
        return characterMovieService.findByAge(age);
    }

    @GetMapping(params = "movies")
    public List<CharacterMovie> findByMovies(@RequestParam("movie") String movie) {
        return characterMovieService.findByMovies(movie);
    }

}