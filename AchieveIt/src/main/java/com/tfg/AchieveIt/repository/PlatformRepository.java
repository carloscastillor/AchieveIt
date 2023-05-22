package com.tfg.AchieveIt.repository;

import com.tfg.AchieveIt.domain.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformRepository extends JpaRepository<Platform, Long> {
    Platform findPlatformByName(String platformName);
}
