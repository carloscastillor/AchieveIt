package com.tfg.AchieveIt.services;

import com.tfg.AchieveIt.domain.User;
import com.tfg.AchieveIt.repository.UserRepository;
import com.tfg.AchieveIt.webRest.security.CustomOAuth2User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Key secret = MacProvider.generateKey(SignatureAlgorithm.HS256);
    private static final byte[] secretBytes = secret.getEncoded();
    private static final String base64SecretBytes = Base64.getEncoder().encodeToString(secretBytes);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User processOAuthPostLogin(CustomOAuth2User user) {

        Optional<User> existingUser = Optional.ofNullable(userRepository.findUserByEmail(user.getEmail()));
        if (existingUser.isPresent()) {
            User oldUser =  existingUser.get();
            oldUser.setLoggedIn(true);
            userRepository.save(oldUser);
            String token = Jwts.builder()
                    .setSubject(oldUser.getId().toString())
                    .signWith(SignatureAlgorithm.HS256, base64SecretBytes)
                    .compact();

            oldUser.setToken(token);

            return oldUser;
        } else {

            User newUser = new User();
            newUser.setEmail(user.getEmail());
            newUser.setProvider(User.Provider.GOOGLE);
            newUser.setName(user.getName());
            newUser.setUserName("userName");
            newUser.setLoggedIn(true);
            userRepository.save(newUser);
            String token = Jwts.builder()
                    .setSubject(newUser.getId().toString())
                    .signWith(SignatureAlgorithm.HS256, base64SecretBytes)
                    .compact();

            newUser.setToken(token);
            return newUser;
        }
    }

    public void processOAuthPostLogout(CustomOAuth2User user) {
        User existingUser = userRepository.findUserByEmail(user.getEmail());
        if (existingUser != null) {
            existingUser.setLoggedIn(false);
            existingUser.setToken(null);
            userRepository.save(existingUser);
        }
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) authentication.getPrincipal();

        String email = principal.getAttribute("email");
        String name = principal.getAttribute("name");

        User user = userRepository.findUserByEmail(email);
        return user;
    }

    public void addRecentVideogame(User user, Long id){
        List<Long> recentVideogames = user.getRecentVideogames();
        recentVideogames.add(0,id);

        if(recentVideogames.size()>5){
            recentVideogames.remove(recentVideogames.size()-1);
        }

        user.setRecentVideogames(recentVideogames);
        userRepository.save(user);
    }

    public void addRecentAchievement(User user, Long id){

        List<Long> recentAchievements = user.getRecentAchievements();
        recentAchievements.add(0, id);

        if(recentAchievements.size()>5){
            recentAchievements.remove(recentAchievements.size()-1);
        }

        user.setRecentAchievements(recentAchievements);
        userRepository.save(user);
    }

    public String getJwtSecret() {
        return base64SecretBytes;
    }
}
