package com.tfg.AchieveIt.webRest;


import com.tfg.AchieveIt.domain.Publisher;
import com.tfg.AchieveIt.repository.PublisherRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PublisherController {

    private final PublisherRepository publisherRepository;

    public PublisherController(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @GetMapping("/publishers")
    public List<Publisher> getAllPublishers() {

        return publisherRepository.findAll();
    }

    @GetMapping("/publishers/{id}")
    public Publisher getPublisher(@PathVariable Long id) {
        return publisherRepository.findById(id).orElseThrow(()-> new RuntimeException());
    }

    @DeleteMapping("/publishers/{id}")
    public void deletePublisher(@PathVariable Long id) {
        publisherRepository.deleteById(id);
    }
}
