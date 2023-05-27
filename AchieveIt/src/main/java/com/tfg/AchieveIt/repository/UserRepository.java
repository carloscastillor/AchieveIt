package com.tfg.AchieveIt.repository;

import com.tfg.AchieveIt.domain.User;
import com.tfg.AchieveIt.domain.Videogame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
