package com.tfg.AchieveIt.webRest;

import com.tfg.AchieveIt.domain.PersonalizedAchievement;
import com.tfg.AchieveIt.repository.PersonalizedAchievementRepository;
import com.tfg.AchieveIt.repository.UserRepository;
import com.tfg.AchieveIt.repository.VideogameRepository;
import com.tfg.AchieveIt.services.PersonalizedAchievementService;
import com.tfg.AchieveIt.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PersonalizedAchievementController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final VideogameRepository videogameRepository;
    private final PersonalizedAchievementRepository personalizedAchievementRepository;

    private final PersonalizedAchievementService personalizedAchievementService;

    public PersonalizedAchievementController(UserService userService, UserRepository userRepository, VideogameRepository videogameRepository, PersonalizedAchievementRepository personalizedAchievementRepository, PersonalizedAchievementService personalizedAchievementService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.videogameRepository = videogameRepository;
        this.personalizedAchievementRepository = personalizedAchievementRepository;
        this.personalizedAchievementService = personalizedAchievementService;
    }

    @GetMapping("/personalized-achievements")
    public List<PersonalizedAchievement> getAllPersonalizedAchievements() {

        return personalizedAchievementRepository.findAll();
    }

    @GetMapping("/personalized-achievements/{id}")
    public PersonalizedAchievement getPersonalizedAchievement(@PathVariable Long id) {
        return personalizedAchievementRepository.findById(id).orElseThrow(()-> new RuntimeException());
    }

    @DeleteMapping("/personalized-achievements/{id}")
    public void deletePersonalizedAchievement(@PathVariable Long id) {
        personalizedAchievementRepository.deleteById(id);
    }

    @PostMapping("/personalized-achievements/create/{token}")
    @Transactional
    public void CreatePersonalizedAchievement(@RequestBody Map<String, String> requestBody, @PathVariable("token") String token){
        String name = requestBody.get("name");
        String description = requestBody.get("description");
        Long videogameId = Long.parseLong(requestBody.get("videogameId"));


        Claims claims = Jwts.parserBuilder().setSigningKey(userService.getJwtSecret()).build().parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        Long uId = Long.parseLong(userId);

        personalizedAchievementService.createPersonalizedAchievement(name, description, uId, videogameId);
    }
}
