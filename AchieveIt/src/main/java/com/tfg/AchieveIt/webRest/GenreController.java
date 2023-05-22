package com.tfg.AchieveIt.webRest;


import com.tfg.AchieveIt.domain.Genre;
import com.tfg.AchieveIt.repository.GenreRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GenreController {

    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping("/genres")
    public List<Genre> getAllGenres() {

        return genreRepository.findAll();
    }

    @GetMapping("/genres/{id}")
    public Genre getGenre(@PathVariable Long id) {
        return genreRepository.findById(id).orElseThrow(()-> new RuntimeException());
    }

    @DeleteMapping("/genres/{id}")
    public void deleteGenre(@PathVariable Long id) {
        genreRepository.deleteById(id);
    }
}
