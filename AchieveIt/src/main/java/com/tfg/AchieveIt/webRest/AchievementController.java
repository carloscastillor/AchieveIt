package com.tfg.AchieveIt.webRest;

import com.tfg.AchieveIt.domain.Achievement;
import com.tfg.AchieveIt.repository.AchievementRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AchievementController {

    private final AchievementRepository achievementRepository;

    public AchievementController(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    @GetMapping("/achievements")
    public List<Achievement> getAllAchievements() {

        return achievementRepository.findAll();
    }

    @GetMapping("/achievements/{id}")
    public Achievement getAchievement(@PathVariable Long id) {
        return achievementRepository.findById(id).orElseThrow(()-> new RuntimeException());
    }

    @DeleteMapping("/achievements/{id}")
    public void deleteAchievement(@PathVariable Long id) {
        achievementRepository.deleteById(id);
    }
}
