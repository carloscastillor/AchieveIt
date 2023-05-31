package com.tfg.AchieveIt.services;

import com.tfg.AchieveIt.domain.User;
import com.tfg.AchieveIt.repository.UserRepository;
import com.tfg.AchieveIt.webRest.security.CustomOAuth2User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User processOAuthPostLogin(CustomOAuth2User user) {

        Optional<User> existingUser = Optional.ofNullable(userRepository.findUserByEmail(user.getEmail()));
        if (existingUser.isPresent()) {
            return null;
        } else {
            User newUser = new User();
            newUser.setEmail(user.getEmail());
            newUser.setProvider(User.Provider.GOOGLE);
            newUser.setName(user.getName());
            newUser.setUserName("userName");
            newUser.setLoggedIn(true);
            userRepository.save(newUser);
            return newUser;
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
}
