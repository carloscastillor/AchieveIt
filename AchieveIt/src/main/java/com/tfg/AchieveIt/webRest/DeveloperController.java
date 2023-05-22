package com.tfg.AchieveIt.webRest;


import com.tfg.AchieveIt.domain.Developer;
import com.tfg.AchieveIt.repository.DeveloperRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DeveloperController {

    private final DeveloperRepository developerRepository;

    public DeveloperController(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    @GetMapping("/developers")
    public List<Developer> getAllDevelopers() {

        return developerRepository.findAll();
    }

    @GetMapping("/developers/{id}")
    public Developer getDeveloper(@PathVariable Long id) {
        return developerRepository.findById(id).orElseThrow(()-> new RuntimeException());
    }

    @DeleteMapping("/developers/{id}")
    public void deleteDeveloper(@PathVariable Long id) {
        developerRepository.deleteById(id);
    }
}
