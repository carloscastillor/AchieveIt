package com.tfg.AchieveIt.repository;

import com.tfg.AchieveIt.domain.PersonalizedAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalizedAchievementRepository extends JpaRepository<PersonalizedAchievement, Long> {
}
