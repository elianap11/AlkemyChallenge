package JavaChallenge.demo.controllers;

import JavaChallenge.demo.entities.Genre;
import JavaChallenge.demo.entities.Movie;
import JavaChallenge.demo.services.GenreService;
import JavaChallenge.demo.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public Genre saveGenre(@RequestBody Genre genre, MultipartFile photo) throws Exception{
      /*  if (!photo.isEmpty()) {
            genre.setImage(photoService.copyPhoto(photo));
        }*/
        return genreService.createGenre(genre);
    }

    @DeleteMapping(path = "delete/{id}")
    public String deleteGenre(@PathVariable("id") Integer id){
        try {
            genreService.delete(id);
            return "La película "+id+" fue eliminada";
        } catch (Exception e) {
            return "La película "+id+" no existe";
        }
    }

    @GetMapping(path = "enable/{id}")
    public String enable(@PathVariable("id") Integer id) {
        try {
            genreService.enable(id);
            return "El personaje " + id + " fue habilitado";
        } catch (Exception e) {
            return "El personaje " + id + " no existe";
        }
    }

}
