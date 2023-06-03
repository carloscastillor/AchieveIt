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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class AchievementController {

    private final AchievementRepository achievementRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    private final VideogameRepository videogameRepository;

    public AchievementController(AchievementRepository achievementRepository, UserService userService, UserRepository userRepository, VideogameRepository videogameRepository) {
        this.achievementRepository = achievementRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.videogameRepository = videogameRepository;
    }

    @GetMapping("/achievements")
    public List<Achievement> getAllAchievements() {

        return achievementRepository.findAll();
    }

    @GetMapping("/achievements/{id}")
    public Achievement getAchievement(@PathVariable Long id) {
        return achievementRepository.findById(id).orElseThrow(()-> new RuntimeException());
    }

    @DeleteMapping("/achievements/{id}")
    public void deleteAchievement(@PathVariable Long id) {
        achievementRepository.deleteById(id);
    }

    @GetMapping("/achievements/videogame/{id}/user/{token}")
    public Set<Long> getUserAchievementsForGame(@PathVariable("id") String id, @PathVariable("token") String token) {

        Claims claims = Jwts.parserBuilder().setSigningKey(userService.getJwtSecret()).build().parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        Long uId = Long.parseLong(userId);

        Long videogameId = Long.parseLong(id);

        Optional<Videogame> OptVideogame = videogameRepository.findById(videogameId);
        if (OptVideogame.isPresent()) {
            return userRepository.findUserAchievementsForGame(uId, videogameId);

        } else {
            throw new RuntimeException("El videojuego no existe");
        }
    }

    @PostMapping("/achievements/add/{token}")
    @Transactional
    public void addAchievementToUser(@RequestBody Map<String, Long> requestBody, @PathVariable("token") String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(userService.getJwtSecret()).build().parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        Long id = Long.parseLong(userId);

        Optional<User> currentUser = userRepository.findById(id);

        Long achievementId = requestBody.get("achievementId");

        Optional<Achievement> OptAchievement = achievementRepository.findById(achievementId);
        if (OptAchievement.isPresent()) {
            Achievement achievement = OptAchievement.get();
            currentUser.get().addAchievement(achievement);
        } else {
            throw new RuntimeException("El logro no existe");
        }
    }

    @PostMapping("/achievements/remove/{token}")
    @Transactional
    public void removeAchievementFromUser(@RequestBody Map<String, Long> requestBody, @PathVariable("token") String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(userService.getJwtSecret()).build().parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        Long id = Long.parseLong(userId);

        Optional<User> currentUser = userRepository.findById(id);

        Long achievementId = requestBody.get("achievementId");
        Optional<Achievement> OptAchievement = achievementRepository.findById(achievementId);
        if (OptAchievement.isPresent()) {
            Achievement achievement = OptAchievement.get();
            currentUser.get().removeAchievement(achievement);
        } else {
            throw new RuntimeException("El logro no existe");
        }

    }
}
