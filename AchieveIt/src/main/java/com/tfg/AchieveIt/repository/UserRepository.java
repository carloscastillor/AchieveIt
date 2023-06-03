package com.tfg.AchieveIt.repository;

import com.tfg.AchieveIt.domain.User;
import com.tfg.AchieveIt.domain.Videogame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    @Query("SELECT a.id FROM User u JOIN u.achievements a JOIN u.videogames v WHERE u.id = :userId AND v.id = :videogameId")
    Set<Long> findUserAchievementsForGame(@Param("userId") Long userId, @Param("videogameId") Long videogameId);
}
