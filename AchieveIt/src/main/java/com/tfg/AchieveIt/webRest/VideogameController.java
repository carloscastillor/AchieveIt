package com.tfg.AchieveIt.webRest;

import com.tfg.AchieveIt.domain.*;
import com.tfg.AchieveIt.repository.GenreRepository;
import com.tfg.AchieveIt.repository.UserRepository;
import com.tfg.AchieveIt.repository.VideogameRepository;
import com.tfg.AchieveIt.services.IGDBApiClient;
import com.tfg.AchieveIt.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class VideogameController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final VideogameRepository videogameRepository;
    private final GenreRepository genreRepository;

    private final IGDBApiClient igdbApiClient = new IGDBApiClient();


    public VideogameController(UserService userService, UserRepository userRepository, VideogameRepository videogameRepository, GenreRepository genreRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.videogameRepository = videogameRepository;
        this.genreRepository = genreRepository;
    }

    @GetMapping("/videogames")
    public List<Videogame> getAllVideogames(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int pageSize) {
        int startIndex = page * pageSize;
        List<Videogame> videogames = videogameRepository.findAll();

        int endIndex = Math.min(startIndex + pageSize, videogames.size());
        return videogames.subList(startIndex, endIndex);
    }

    @GetMapping("/videogames/genres")
    public List<Videogame> getVideogamesByGenre(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int pageSize, @RequestParam(defaultValue = "") String genre) {

        int startIndex = page * pageSize;
        Optional<Genre> genreDb = genreRepository.findById(Long.parseLong(genre));
        List<Videogame> videogames = videogameRepository.findVideogamesByGenres(genreDb.get());

        int endIndex = Math.min(startIndex + pageSize, videogames.size());
        return videogames.subList(startIndex, endIndex);
    }
    @GetMapping("/videogames/name")
    public List<Videogame> getVideogamesByName(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int pageSize, @RequestParam(defaultValue = "") String name) {

        int startIndex = page * pageSize;
        List<Videogame> videogames = videogameRepository.findVideogameByNameContaining(name);;

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

    @PostMapping("/videogames/add/{token}")
    @Transactional
    public void addUserVideogame(@RequestBody Map<String, Long> requestBody, @PathVariable("token") String token) {

        Claims claims = Jwts.parserBuilder().setSigningKey(userService.getJwtSecret()).build().parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        Long id = Long.parseLong(userId);

        Optional<User> currentUser = userRepository.findById(id);

        Long videogameId = requestBody.get("videogameId");

        Optional<Videogame> OptVideogame = videogameRepository.findById(videogameId);
        if (OptVideogame.isPresent()) {
            Videogame videogame = OptVideogame.get();
            currentUser.get().addVideogame(videogame);
            userService.addRecentVideogame(currentUser.get(), videogameId);
        } else {
            throw new RuntimeException("El videojuego no existe");
        }
    }

    @PostMapping("/videogames/delete/{token}")
    @Transactional
    public void deleteUserVideogame(@RequestBody Map<String, Long> requestBody, @PathVariable("token") String token) {

        Claims claims = Jwts.parserBuilder().setSigningKey(userService.getJwtSecret()).build().parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        Long id = Long.parseLong(userId);

        Optional<User> currentUser = userRepository.findById(id);

        Long videogameId = requestBody.get("videogameId");

        Optional<Videogame> OptVideogame = videogameRepository.findById(videogameId);
        if (OptVideogame.isPresent()) {
            Videogame videogame = OptVideogame.get();
            currentUser.get().removeVideogame(videogame);
        } else {
            throw new RuntimeException("El videojuego no existe");
        }
    }

    @GetMapping("/videogames/{id}/{token}")
    @Transactional
    public boolean getUserVideogame(@PathVariable("id") String id, @PathVariable("token") String token) {

        Claims claims = Jwts.parserBuilder().setSigningKey(userService.getJwtSecret()).build().parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        Long userIdL = Long.parseLong(userId);

        Optional<User> currentUser = userRepository.findById(userIdL);

        Long videogameId = Long.parseLong(id);

        Optional<Videogame> OptVideogame = videogameRepository.findById(videogameId);
        if (currentUser.get().getVideogames().contains(OptVideogame.get())) {
            return true;
        } else if(!currentUser.get().getVideogames().contains(OptVideogame.get())){
            return false;
        }else{
            throw new RuntimeException("El videojuego no existe");
        }
    }

    @GetMapping("/videogames/{videogameName}/cover")
    public String getVideogameCoverUrl(@PathVariable("videogameName") String videogameName) {
        try {
            String coverUrl = igdbApiClient.getCoverUrl(videogameName);
            return coverUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
