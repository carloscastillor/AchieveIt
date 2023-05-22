package com.tfg.AchieveIt.webRest;

import com.tfg.AchieveIt.services.DeveloperServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


    private final DeveloperServices developerServices;
    @Autowired
    public HomeController(DeveloperServices developerServices) {
        this.developerServices = developerServices;
    }

    @GetMapping("/Home")
    public String getHome(){

        return "aaaaaaaaaa";
    }
}
