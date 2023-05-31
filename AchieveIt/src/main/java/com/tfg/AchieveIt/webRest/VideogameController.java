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
    public List<Videogame> getAllVideogames(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int pageSize) {
        int startIndex = page * pageSize;
        List<Videogame> videogames = videogameRepository.findAll();

        int endIndex = Math.min(startIndex + pageSize, videogames.size());
        return videogames.subList(startIndex, endIndex);
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
            currentUser.get().addVideogame(videogame);
        } else {
            throw new RuntimeException("El videojuego no existe");
        }
    }
}
