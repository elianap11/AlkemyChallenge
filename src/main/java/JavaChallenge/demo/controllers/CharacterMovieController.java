package JavaChallenge.demo.controllers;

import JavaChallenge.demo.entities.CharacterMovie;
import JavaChallenge.demo.services.CharacterMovieService;
import JavaChallenge.demo.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

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

    //Le puse List<Object[]>
    @GetMapping
    public List<Object[]> showListCharacterMovie() {
        return characterMovieService.showListCharacterMovie();
    }

    @GetMapping("/all")
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

    //REVISAR
    @GetMapping(value = "", params = "id")
    public List<CharacterMovie> findByMovie(@RequestParam("id") Integer id) {
        return characterMovieService.findByMovie(id);
    }


    @PostMapping("/save")
    public CharacterMovie saveCharacterMovie(/*@RequestParam("image") MultipartFile photo*/ @RequestBody CharacterMovie characterMovie, BindingResult result) {
    /*    if (!photo.isEmpty()) {
            characterMovie.setImage(photoService.copyPhoto(photo));
        }*/


        return characterMovieService.createCharacterMovie(characterMovie);
    }

    @DeleteMapping(path = "delete/{id}")
    public String deleteCharacter(@PathVariable("id") Integer id){
        try {
            characterMovieService.delete(id);
            return "El personaje "+id+" fue eliminado";
        } catch (Exception e) {
            return "El personaje "+id+" no existe";
        }
    }

    @GetMapping(path = "enable/{id}")
    public String enable(@PathVariable("id") Integer id){
        try {
            characterMovieService.enable(id);
            return "El personaje "+id+" fue habilitado";
        } catch (Exception e) {
            return "El personaje "+id+" no existe";
        }
    }


}