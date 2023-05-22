package com.tfg.AchieveIt.services;

import com.tfg.AchieveIt.AchieveItApplication;
import com.tfg.AchieveIt.domain.Developer;
import com.tfg.AchieveIt.repository.DeveloperRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeveloperServices {

    private static final Logger log = LoggerFactory.getLogger(AchieveItApplication.class);

    private final DeveloperRepository repository;

    public DeveloperServices(DeveloperRepository repository) {
        this.repository = repository;
    }

    public void fillDeveloper(Developer dev){
        repository.save(dev);

        // fetch all customers
        Optional<Developer> developer = repository.findById(1L);
        log.info("Customer found with findById(1L):");
        log.info("--------------------------------");
        log.info(developer.toString());
        log.info("");
    }
}
