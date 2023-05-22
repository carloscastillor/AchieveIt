package com.tfg.AchieveIt.repository;

import com.tfg.AchieveIt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
