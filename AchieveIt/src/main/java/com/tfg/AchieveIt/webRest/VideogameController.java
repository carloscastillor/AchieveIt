package com.tfg.AchieveIt.webRest;

import com.tfg.AchieveIt.domain.*;
import com.tfg.AchieveIt.repository.VideogameRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class VideogameController {

    private final VideogameRepository videogameRepository;


    public VideogameController(VideogameRepository videogameRepository) {
        this.videogameRepository = videogameRepository;
    }

    @GetMapping("/videogames")
    public List<Videogame> getAllVideogames() {
        List<Videogame> videogames = videogameRepository.findAll();
        for (Videogame videogame : videogames) {
            System.out.println(videogame.getName());
        }

        return videogameRepository.findAll();
    }

    @GetMapping("/videogames/{id}")
    public Videogame getVideogame(@PathVariable Long id) {
        return videogameRepository.findById(id).orElseThrow(()-> new RuntimeException());
    }

    @DeleteMapping("/videogames/{id}")
    public void deleteVideogame(@PathVariable Long id) {
        videogameRepository.deleteById(id);
    }
}
