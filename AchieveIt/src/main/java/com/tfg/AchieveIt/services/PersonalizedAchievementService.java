package com.tfg.AchieveIt.services;

import com.tfg.AchieveIt.domain.PersonalizedAchievement;
import com.tfg.AchieveIt.domain.User;
import com.tfg.AchieveIt.domain.Videogame;
import com.tfg.AchieveIt.repository.PersonalizedAchievementRepository;
import com.tfg.AchieveIt.repository.UserRepository;
import com.tfg.AchieveIt.repository.VideogameRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonalizedAchievementService {

    private final PersonalizedAchievementRepository personalizedAchievementRepository;
    private final VideogameRepository videogameRepository;
    private final UserRepository userRepository;


    public PersonalizedAchievementService(PersonalizedAchievementRepository personalizedAchievementRepository, VideogameRepository videogameRepository, UserRepository userRepository) {
        this.personalizedAchievementRepository = personalizedAchievementRepository;
        this.videogameRepository = videogameRepository;
        this.userRepository = userRepository;
    }

    public void createPersonalizedAchievement(String name, String description, Long userId, Long videogameId){
        PersonalizedAchievement personalizedAchievement = new PersonalizedAchievement();

        Videogame videogame = videogameRepository.findById(videogameId).get();
        User user = userRepository.findById(userId).get();

        personalizedAchievement.setLikes(0);
        personalizedAchievement.setName(name);
        personalizedAchievement.setDescription(description);
        personalizedAchievement.setVideogame(videogame);
        personalizedAchievement.setUser(user);

        user.addCreatedPersonalizedAchievement(personalizedAchievement);
        videogame.addPersonalizedAchievement(personalizedAchievement);

        personalizedAchievementRepository.save(personalizedAchievement);
    }
}
