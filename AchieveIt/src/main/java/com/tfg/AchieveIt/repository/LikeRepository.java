package com.tfg.AchieveIt.repository;

import com.tfg.AchieveIt.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByPersonalizedAchievementIdAndUserId(Long achievementId, Long userId);

    @Query("SELECT l FROM Like l WHERE l.user.id = :userId AND l.personalizedAchievement.id = :personalizedAchievementId")
    Like findByUserIdAndPersonalizedAchievementId(@Param("userId") Long userId, @Param("personalizedAchievementId") Long personalizedAchievementId);

}
