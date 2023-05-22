package com.tfg.AchieveIt.repository;

import com.tfg.AchieveIt.domain.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    Developer findDeveloperByName(String developerName);
}
