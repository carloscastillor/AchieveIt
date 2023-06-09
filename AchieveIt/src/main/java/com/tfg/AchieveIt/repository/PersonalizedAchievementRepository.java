package com.tfg.AchieveIt.repository;

import com.tfg.AchieveIt.domain.PersonalizedAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonalizedAchievementRepository extends JpaRepository<PersonalizedAchievement, Long> {

    List<PersonalizedAchievement> findPersonalizedAchievementByVideogameId(Long videogameId);

    List<PersonalizedAchievement> findPersonalizedAchievementByUserId(Long userId);
}
