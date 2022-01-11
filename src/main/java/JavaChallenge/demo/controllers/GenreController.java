package JavaChallenge.demo.controllers;

import JavaChallenge.demo.entities.Genre;
import JavaChallenge.demo.services.GenreService;
import JavaChallenge.demo.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @Autowired
    private PhotoService photoService;

    @GetMapping
    public List<Genre> getAll() {
        return genreService.showGenre();
    }

    @PostMapping("/save")
    public Genre saveGenre(@Valid @ModelAttribute Genre genre, BindingResult result, @RequestParam (value = "image") MultipartFile photo) throws Exception {
        if (!photo.isEmpty()) {
            genre.setImage(photoService.copyPhoto(photo));
        }
        return genreService.createGenre(genre, photo);
    }

    @GetMapping("/{id}")
    public Optional<Genre> getById(@PathVariable("id") Integer id) {
        return genreService.findById(id);
    }

    @DeleteMapping(path = "delete/{id}")
    public String deleteGenre(@PathVariable("id") Integer id) {
        try {
            genreService.delete(id);
            return "El género " + id + " fue eliminado";
        } catch (Exception e) {
            return "El género " + id + " no existe";
        }
    }

    @GetMapping(path = "enable/{id}")
    public String enable(@PathVariable("id") Integer id) {
        try {
            genreService.enable(id);
            return "El género " + id + " fue habilitado";
        } catch (Exception e) {
            return "El género " + id + " no existe";
        }
    }

}
