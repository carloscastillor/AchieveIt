package com.tfg.AchieveIt.webRest;

import com.tfg.AchieveIt.domain.User;
import com.tfg.AchieveIt.domain.Videogame;
import com.tfg.AchieveIt.repository.UserRepository;
import com.tfg.AchieveIt.webRest.security.SecurityConfiguration;
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
    private final SecurityConfiguration securityConfiguration;

    public UserController(UserRepository userRepository, SecurityConfiguration securityConfiguration) {
        this.userRepository = userRepository;
        this.securityConfiguration = securityConfiguration;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @GetMapping("/users/{token}")
    public User getUser(@PathVariable("token") String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(securityConfiguration.getJwtSecret()).build().parseClaimsJws(token).getBody();
        String userId = claims.getSubject();
        Long id = Long.parseLong(userId);

        System.out.println(id);

        return userRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @GetMapping("/users/{userId}/videogames")
    public List<Videogame> getUserVideogames(@PathVariable("userId") Long userId) {

        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return new ArrayList<Videogame>(user.getVideogames());
        } else {
            throw new RuntimeException("El usuario no existe");
        }
    }
}
