package com.tfg.AchieveIt.repository;

import com.tfg.AchieveIt.domain.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementRepository extends JpaRepository<Achievement, Long> { }
