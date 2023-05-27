package com.tfg.AchieveIt.webRest;

import com.tfg.AchieveIt.domain.*;
import com.tfg.AchieveIt.repository.UserRepository;
import com.tfg.AchieveIt.repository.VideogameRepository;
import com.tfg.AchieveIt.services.UserService;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public void addUserVideogame(@RequestBody Map<String, Long> requestBody) {

        Long videogameId = requestBody.get("videogameId");

        //User currentUser = userService.getCurrentUser();
        Optional<User> currentUser = userRepository.findById(1L);

        Optional<Videogame> OptVideogame = videogameRepository.findById(videogameId);
        if (OptVideogame.isPresent()) {
            Videogame videogame = OptVideogame.get();
            System.out.println(currentUser.get().getVideogames().size());
            currentUser.get().addVideogame(videogame);
            System.out.println(currentUser.get().getVideogames().size());
        } else {
            throw new RuntimeException("El videojuego no existe");
        }
    }
}
