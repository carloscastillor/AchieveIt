package com.tfg.AchieveIt.webRest;

import com.tfg.AchieveIt.domain.*;
import com.tfg.AchieveIt.repository.UserRepository;
import com.tfg.AchieveIt.repository.VideogameRepository;
import com.tfg.AchieveIt.services.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class VideogameController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final VideogameRepository videogameRepository;


    public VideogameController(UserService userService, UserRepository userRepository, VideogameRepository videogameRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
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

    @PostMapping("/videogames/add")
    public void addUserVideogame(@RequestBody Map<String, Long> requestBody) {

        System.out.println("entro");
        Long videogameId = requestBody.get("id");

        User currentUser = userService.getCurrentUser();

        Optional<Videogame> OptVideogame = videogameRepository.findById(videogameId);
        if (OptVideogame.isPresent()) {
            Videogame videogame = OptVideogame.get();
            currentUser.addVideogame(videogame);
        } else {
            throw new RuntimeException("El videojuego no existe");
        }
    }
}
