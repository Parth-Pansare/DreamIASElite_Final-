package com.dreamias.backend.repository;

import com.dreamias.backend.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository interface for Subject entity.
 * JpaRepository provides standard CRUD methods (save, findById, findAll, delete, etc.).
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    
    /**
     * Find a subject by its exact name.
     * Spring generates the query automatically based on the method name.
     */
    Optional<Subject> findByName(String name);
}
