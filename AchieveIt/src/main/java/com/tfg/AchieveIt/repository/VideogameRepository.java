package com.tfg.AchieveIt.repository;

import com.tfg.AchieveIt.domain.User;
import com.tfg.AchieveIt.domain.Videogame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideogameRepository extends JpaRepository<Videogame, Long> {
    Videogame findVideogameByName(String name);
}
