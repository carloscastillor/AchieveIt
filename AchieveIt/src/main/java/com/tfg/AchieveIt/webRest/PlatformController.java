package com.tfg.AchieveIt.webRest;


import com.tfg.AchieveIt.domain.Platform;
import com.tfg.AchieveIt.repository.PlatformRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlatformController {

    private final PlatformRepository platformRepository;

    public PlatformController(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    @GetMapping("/platforms")
    public List<Platform> getAllPlatforms() {

        return platformRepository.findAll();
    }

    @GetMapping("/platforms/{id}")
    public Platform getPlatform(@PathVariable Long id) {
        return platformRepository.findById(id).orElseThrow(()-> new RuntimeException());
    }

    @DeleteMapping("/platforms/{id}")
    public void deletePlatform(@PathVariable Long id) {
        platformRepository.deleteById(id);
    }
}
