package com.tfg.AchieveIt.webRest;

import com.tfg.AchieveIt.domain.PersonalizedAchievement;
import com.tfg.AchieveIt.domain.User;
import com.tfg.AchieveIt.domain.Videogame;
import com.tfg.AchieveIt.repository.LikeRepository;
import com.tfg.AchieveIt.repository.PersonalizedAchievementRepository;
import com.tfg.AchieveIt.repository.UserRepository;
import com.tfg.AchieveIt.repository.VideogameRepository;
import com.tfg.AchieveIt.services.PersonalizedAchievementService;
import com.tfg.AchieveIt.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class PersonalizedAchievementController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final VideogameRepository videogameRepository;
    private final PersonalizedAchievementRepository personalizedAchievementRepository;
    private final PersonalizedAchievementService personalizedAchievementService;
    private final LikeRepository likeRepository;

    public PersonalizedAchievementController(UserService userService, UserRepository userRepository, VideogameRepository videogameRepository, PersonalizedAchievementRepository personalizedAchievementRepository, PersonalizedAchievementService personalizedAchievementService, LikeRepository likeRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.videogameRepository = videogameRepository;
        this.personalizedAchievementRepository = personalizedAchievementRepository;
        this.personalizedAchievementService = personalizedAchievementService;
        this.likeRepository = likeRepository;
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

    @GetMapping("/personalized-achievements/{id}/user")
    public User getUserOfPersonalizedAchievement(@PathVariable("id") Long id) {

        Optional<PersonalizedAchievement> OptPersonalizedAchievement =personalizedAchievementRepository.findById(id);

        if(OptPersonalizedAchievement.isPresent()){
            PersonalizedAchievement personalizedAchievement = OptPersonalizedAchievement.get();
            User user = personalizedAchievement.getUser();
            return user;
        }

        return null;
    }
    @GetMapping("/personalized-achievements/videogame/{id}")
    public List<PersonalizedAchievement> getPersonalizedAchievementByVideogame(@PathVariable("id") Long id) {
        return personalizedAchievementRepository.findPersonalizedAchievementByVideogameId(id);
    }

    @GetMapping("/personalized-achievements/user/{token}")
    public Map<String, List<PersonalizedAchievement>> getPersonalizedAchievementFromUser(@PathVariable("token") String token) {

        Long id = 0L;
        if (userService.isTokenJwt(token)) {
            Claims claims = Jwts.parserBuilder().setSigningKey(userService.getJwtSecret()).build().parseClaimsJws(token).getBody();
            String userId = claims.getSubject();
            id = Long.parseLong(userId);
        }else{
            id = Long.parseLong(token);
        }

        List<PersonalizedAchievement> personalizedAchievements = personalizedAchievementRepository.findPersonalizedAchievementByUserId(id);

        Map<String, List<PersonalizedAchievement>> result = new HashMap<>();

        for(PersonalizedAchievement personalizedAchievement : personalizedAchievements){
            String videogameName = personalizedAchievement.getVideogame().getName();
            result.computeIfAbsent(videogameName, k -> new ArrayList<>()).add(personalizedAchievement);
        }

        return result;
    }

    @GetMapping("/personalized-achievements/user/{token}/likes")
    public Integer getAllLikesOfUser(@PathVariable("token") String token) {

        Long id = 0L;
        if (userService.isTokenJwt(token)) {
            Claims claims = Jwts.parserBuilder().setSigningKey(userService.getJwtSecret()).build().parseClaimsJws(token).getBody();
            String userId = claims.getSubject();
            id = Long.parseLong(userId);
        }else{
            id = Long.parseLong(token);
        }

        List<PersonalizedAchievement> personalizedAchievements = personalizedAchievementRepository.findPersonalizedAchievementByUserId(id);

        int result = 0;
        for(PersonalizedAchievement personalizedAchievement : personalizedAchievements){

            result = result +personalizedAchievement.getLikesNum();
        }

        return result;
    }


    @GetMapping("/personalized-achievements/videogame/{id}/user/{token}")
    public Set<Long> getUserPersonalizedAchievementsForGame(@PathVariable("id") String id, @PathVariable("token") String token) {

        Claims claims = Jwts.parserBuilder().setSigningKey(userService.getJwtSecret()).build().parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        Long uId = Long.parseLong(userId);

        Long videogameId = Long.parseLong(id);

        Optional<Videogame> OptVideogame = videogameRepository.findById(videogameId);
        if (OptVideogame.isPresent()) {
            return userRepository.findUserPersonalizedAchievementsForGame(uId, videogameId);
        } else {
            throw new RuntimeException("El videojuego no existe");
        }
    }

    @PostMapping("/personalized-achievements/add/{token}")
    @Transactional
    public void AddPersonalizedAchievement(@RequestBody Map<String, Long> requestBody, @PathVariable("token") String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(userService.getJwtSecret()).build().parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        Long id = Long.parseLong(userId);
        Optional<User> currentUser = userRepository.findById(id);

        Long personalizedAchievementId = requestBody.get("personalizedAchievementId");

        Optional<PersonalizedAchievement> OptPersonalizedAchievement = personalizedAchievementRepository.findById(personalizedAchievementId);
        if (OptPersonalizedAchievement.isPresent()) {
            PersonalizedAchievement personalizedAchievement = OptPersonalizedAchievement.get();
            currentUser.get().addPersonalizedAchievement(personalizedAchievement);
        } else {
            throw new RuntimeException("El logro no existe");
        }
    }

    @PostMapping("/personalized-achievements/remove/{token}")
    @Transactional
    public void RemovePersonalizedAchievement(@RequestBody Map<String, Long> requestBody, @PathVariable("token") String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(userService.getJwtSecret()).build().parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        Long id = Long.parseLong(userId);
        Optional<User> currentUser = userRepository.findById(id);

        Long personalizedAchievementId = requestBody.get("personalizedAchievementId");

        Optional<PersonalizedAchievement> OptPersonalizedAchievement = personalizedAchievementRepository.findById(personalizedAchievementId);
        if (OptPersonalizedAchievement.isPresent()) {
            PersonalizedAchievement personalizedAchievement = OptPersonalizedAchievement.get();
            currentUser.get().removePersonalizedAchievement(personalizedAchievement);
        } else {
            throw new RuntimeException("El logro no existe");
        }
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

    @PostMapping("/personalized-achievements/update")
    @Transactional
    public void UpdatePersonalizedAchievement(@RequestBody Map<String, String> requestBody){
        String name = requestBody.get("name");
        String description = requestBody.get("description");
        Long achievementId = Long.parseLong(requestBody.get("achievementId"));

        personalizedAchievementService.updatePersonalizedAchievement(name, description, achievementId);
    }

    @PostMapping("/personalized-achievements/{personalizedAchievementId}/like/{token}")
    @Transactional
    public void likePersonalizedAchievement(@PathVariable("personalizedAchievementId") String personalizedAchievementId, @PathVariable("token") String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(userService.getJwtSecret()).build().parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        Long uId = Long.parseLong(userId);

        Long personalizedAchievementIdL = Long.parseLong(personalizedAchievementId);

        Optional<PersonalizedAchievement> OptPersonalizedAchievement = personalizedAchievementRepository.findById(personalizedAchievementIdL);

        if(OptPersonalizedAchievement.isPresent()){
            PersonalizedAchievement personalizedAchievement = OptPersonalizedAchievement.get();
            personalizedAchievement.setLikesNum(personalizedAchievement.getLikesNum()+1);
            personalizedAchievementService.likePersonalizedAchievement(personalizedAchievementIdL, uId);
        }else{
            throw new RuntimeException("El logro personalizado no existe");
        }
    }

    @PostMapping("/personalized-achievements/{personalizedAchievementId}/dislike/{token}")
    @Transactional
    public void dislikePersonalizedAchievement(@PathVariable("personalizedAchievementId") String personalizedAchievementId, @PathVariable("token") String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(userService.getJwtSecret()).build().parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        Long uId = Long.parseLong(userId);

        Long personalizedAchievementIdL = Long.parseLong(personalizedAchievementId);

        Optional<PersonalizedAchievement> OptPersonalizedAchievement = personalizedAchievementRepository.findById(personalizedAchievementIdL);

        if(OptPersonalizedAchievement.isPresent()){
            PersonalizedAchievement personalizedAchievement = OptPersonalizedAchievement.get();
            personalizedAchievement.setLikesNum(personalizedAchievement.getLikesNum()-1);
            personalizedAchievementService.dislikePersonalizedAchievement(personalizedAchievementIdL, uId);
        }else{
            throw new RuntimeException("El logro personalizado no existe");
        }
    }

    @GetMapping("/personalized-achievements/{personalizedAchievementId}/liked-by/{token}")
    public boolean isLikedByUser(@PathVariable("personalizedAchievementId") String personalizedAchievementId, @PathVariable("token") String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(userService.getJwtSecret()).build().parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        Long uId = Long.parseLong(userId);

        Long personalizedAchievementIdL = Long.parseLong(personalizedAchievementId);

        Optional<PersonalizedAchievement> OptPersonalizedAchievement = personalizedAchievementRepository.findById(personalizedAchievementIdL);

        if(OptPersonalizedAchievement.isPresent()){
            return likeRepository.existsByPersonalizedAchievementIdAndUserId(personalizedAchievementIdL, uId);
        }else{
            throw new RuntimeException("El me gusta del logro personalizado no existe");
        }
    }

    @GetMapping("/personalized-achievements/videogame/{videogameId}/ranking")
    public List<PersonalizedAchievement> getPersonalizedAchievementsRanking(@PathVariable("videogameId") String videogameId) {
        try {
            Long videogameIdL = Long.parseLong(videogameId);

            Optional<Videogame> OptVideogame = videogameRepository.findById(videogameIdL);

            if(OptVideogame.isPresent()){
                List<PersonalizedAchievement> personalizedAchievements = personalizedAchievementRepository.findPersonalizedAchievementByVideogameId(videogameIdL);
                personalizedAchievements.sort(Comparator.comparingInt(PersonalizedAchievement::getLikesNum).reversed());

                return personalizedAchievements.subList(0, Math.min(10, personalizedAchievements.size()));
            }else{
                throw new RuntimeException("El no existe el videojuego");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
