package JavaChallenge.demo.controllers;

import JavaChallenge.demo.entities.CharacterMovie;
import JavaChallenge.demo.services.CharacterMovieService;
import JavaChallenge.demo.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/characters")
public class CharacterMovieController {

    @Autowired
    private CharacterMovieService characterMovieService;

    @Autowired
    private PhotoService photoService;

    @GetMapping
    public List<Object[]> showCharacterMovie() {
        return characterMovieService.showListCharacterMovie();
    }

    @GetMapping("/all")
    public List<CharacterMovie> getCharacterMovie() {
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

    @RequestMapping(value ="", params = "movies", method = RequestMethod.GET)
    public ResponseEntity<?> findByIdMovie(@RequestParam("movies") Integer idMovie) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(characterMovieService.findAllByIdMovie(idMovie));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"Error. " + e.getMessage() + ".\"}");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public CharacterMovie saveCharacterMovie (@Valid @ModelAttribute CharacterMovie characterMovie,  BindingResult result, @RequestParam(value = "image") MultipartFile photo) throws Exception{
        return characterMovieService.createCharacterMovie(characterMovie, photo);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "delete/{id}")
    public String deleteCharacterMovie(@PathVariable("id") Integer id){
        try {
            characterMovieService.delete(id);
            return "El personaje "+id+" fue eliminado";
        } catch (Exception e) {
            return "El personaje "+id+" no existe";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "enable/{id}")
    public String enableCharacterMovie(@PathVariable("id") Integer id){
        try {
            characterMovieService.enable(id);
            return "El personaje "+id+" fue habilitado";
        } catch (Exception e) {
            return "El personaje "+id+" no existe";
        }
    }
}