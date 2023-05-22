package com.tfg.AchieveIt.services;

import com.tfg.AchieveIt.domain.User;
import com.tfg.AchieveIt.repository.UserRepository;
import com.tfg.AchieveIt.webRest.security.CustomOAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void processOAuthPostLogin(CustomOAuth2User user) {

        Optional<User> existingUser = Optional.ofNullable(userRepository.findUserByEmail(user.getEmail()));
        if (existingUser.isPresent()) {

        } else {
            User newUser = new User();
            newUser.setEmail(user.getEmail());
            newUser.setProvider(User.Provider.GOOGLE);
            newUser.setName(user.getName());
            newUser.setUserName("userName");
            userRepository.save(newUser);
        }
    }
}
