package com.dreamias.backend.repository;

import com.dreamias.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Used for login to find the user by their email.
     */
    Optional<User> findByEmail(String email);

    /**
     * Used during signup to check if the email is already registered.
     */
    boolean existsByEmail(String email);
}
