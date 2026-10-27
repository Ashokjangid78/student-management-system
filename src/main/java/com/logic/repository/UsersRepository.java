package com.logic.repository;

import com.logic.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users , Long> {
    boolean existsByUsername(String username);

    Optional<Users> findByUsername(String username);
}
