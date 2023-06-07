package com.tfg.AchieveIt.services;

import com.tfg.AchieveIt.domain.Like;
import com.tfg.AchieveIt.domain.PersonalizedAchievement;
import com.tfg.AchieveIt.domain.User;
import com.tfg.AchieveIt.domain.Videogame;
import com.tfg.AchieveIt.repository.LikeRepository;
import com.tfg.AchieveIt.repository.PersonalizedAchievementRepository;
import com.tfg.AchieveIt.repository.UserRepository;
import com.tfg.AchieveIt.repository.VideogameRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonalizedAchievementService {

    private final PersonalizedAchievementRepository personalizedAchievementRepository;
    private final VideogameRepository videogameRepository;
    private final UserRepository userRepository;

    private final LikeRepository likeRepository;


    public PersonalizedAchievementService(PersonalizedAchievementRepository personalizedAchievementRepository, VideogameRepository videogameRepository, UserRepository userRepository, LikeRepository likeRepository) {
        this.personalizedAchievementRepository = personalizedAchievementRepository;
        this.videogameRepository = videogameRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
    }

    public void createPersonalizedAchievement(String name, String description, Long userId, Long videogameId){
        PersonalizedAchievement personalizedAchievement = new PersonalizedAchievement();

        Videogame videogame = videogameRepository.findById(videogameId).get();
        User user = userRepository.findById(userId).get();

        personalizedAchievement.setLikesNum(0);
        personalizedAchievement.setName(name);
        personalizedAchievement.setDescription(description);
        personalizedAchievement.setVideogame(videogame);
        personalizedAchievement.setUser(user);

        user.addCreatedPersonalizedAchievement(personalizedAchievement);
        videogame.addPersonalizedAchievement(personalizedAchievement);

        personalizedAchievementRepository.save(personalizedAchievement);
    }

    public void likePersonalizedAchievement(Long personalizedAchievementId, Long userID){
        Like like = new Like();

        User user = userRepository.findById(userID).get();
        PersonalizedAchievement personalizedAchievement = personalizedAchievementRepository.findById(personalizedAchievementId).get();

        like.setPersonalizedAchievement(personalizedAchievement);
        like.setUser(user);

        user.addLike(like);
        personalizedAchievement.addLike(like);

        likeRepository.save(like);
    }

    public void dislikePersonalizedAchievement(Long personalizedAchievementId, Long userId){
        User user = userRepository.findById(userId).get();
        PersonalizedAchievement personalizedAchievement = personalizedAchievementRepository.findById(personalizedAchievementId).get();

        Like like = likeRepository.findByUserIdAndPersonalizedAchievementId(userId, personalizedAchievementId);

        user.removeLike(like);
        personalizedAchievement.removeLike(like);
        likeRepository.delete(like);
    }
}
