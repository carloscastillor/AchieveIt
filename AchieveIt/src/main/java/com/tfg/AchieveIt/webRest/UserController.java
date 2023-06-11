package com.tfg.AchieveIt.webRest;

import com.tfg.AchieveIt.domain.Achievement;
import com.tfg.AchieveIt.domain.User;
import com.tfg.AchieveIt.domain.Videogame;
import com.tfg.AchieveIt.repository.AchievementRepository;
import com.tfg.AchieveIt.repository.UserRepository;
import com.tfg.AchieveIt.repository.VideogameRepository;
import com.tfg.AchieveIt.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final AchievementRepository achievementRepository;
    private final VideogameRepository videogameRepository;

    public UserController(UserRepository userRepository, UserService userService, AchievementRepository achievementRepository, VideogameRepository videogameRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.achievementRepository = achievementRepository;
        this.videogameRepository = videogameRepository;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @GetMapping("/users/{token}")
    public User getUser(@PathVariable("token") String token) {
        Long id = 0L;
        if (userService.isTokenJwt(token)) {
            Claims claims = Jwts.parserBuilder().setSigningKey(userService.getJwtSecret()).build().parseClaimsJws(token).getBody();
            String userId = claims.getSubject();
            id = Long.parseLong(userId);
        }else{
            id = Long.parseLong(token);
        }
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }

    @GetMapping("/users/id/{userId}")
    public User getUserById(@PathVariable("userId") String userId) {
        Long id = Long.parseLong(userId);

        return userRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/users/{token}/videogames")
    public List<Videogame> getUserVideogames(@PathVariable("token") String token) {

        Claims claims = Jwts.parserBuilder().setSigningKey(userService.getJwtSecret()).build().parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        Long id = Long.parseLong(userId);

        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return new ArrayList<>(user.getVideogames());
        } else {
            throw new RuntimeException("El usuario no existe");
        }
    }

    @GetMapping("/users/{token}/videogamesTotal")
    public Integer getUserVideogamesTotal(@PathVariable("token") String token) {

        Long id = 0L;
        if (userService.isTokenJwt(token)) {
            Claims claims = Jwts.parserBuilder().setSigningKey(userService.getJwtSecret()).build().parseClaimsJws(token).getBody();
            String userId = claims.getSubject();
            id = Long.parseLong(userId);
        }else{
            id = Long.parseLong(token);
        }

        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return user.getVideogames().size();
        } else {
            throw new RuntimeException("El usuario no existe");
        }
    }

    @GetMapping("/users/{token}/recent-videogames")
    public List<Videogame> getRecentVideogames(@PathVariable("token") String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(userService.getJwtSecret()).build().parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        Long id = Long.parseLong(userId);

        Optional<User> userOpt = userRepository.findById(id);

        List<Long> recentVideogamesIds = userOpt.get().getRecentVideogames();

        List<Videogame> recentVideogames = new ArrayList<>();

        for (Long idV : recentVideogamesIds) {
            recentVideogames.add(videogameRepository.findById(idV).get());
        }

        return recentVideogames;
    }

    @GetMapping("/users/{token}/recent-achievements")
    public List<Achievement> getRecentAchievements(@PathVariable("token") String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(userService.getJwtSecret()).build().parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        Long id = Long.parseLong(userId);

        Optional<User> userOpt = userRepository.findById(id);

        List<Long> recentAchievementsIds = userOpt.get().getRecentAchievements();

        List<Achievement> recentAchievements = new ArrayList<>();

        for (Long idA : recentAchievementsIds) {
            recentAchievements.add(achievementRepository.findById(idA).get());
        }

        return recentAchievements;
    }
}
