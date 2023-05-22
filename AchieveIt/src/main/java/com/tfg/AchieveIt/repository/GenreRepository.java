package com.tfg.AchieveIt.repository;

import com.tfg.AchieveIt.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findGenreByName(String genreName);
}
