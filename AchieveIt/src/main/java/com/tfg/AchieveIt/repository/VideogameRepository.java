package com.tfg.AchieveIt.repository;

import com.tfg.AchieveIt.domain.Genre;
import com.tfg.AchieveIt.domain.User;
import com.tfg.AchieveIt.domain.Videogame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideogameRepository extends JpaRepository<Videogame, Long> {
    Videogame findVideogameByName(String name);

    List<Videogame> findVideogamesByGenres(Genre genre);

    @Query("SELECT v FROM Videogame v WHERE REGEXP_REPLACE(LOWER(v.name), '[^a-z0-9 ]', '') LIKE LOWER(concat('%', REGEXP_REPLACE(:name, '[^a-z0-9 ]', ''), '%'))")
    List<Videogame> findVideogameByNameContaining(@Param("name") String name);
}
