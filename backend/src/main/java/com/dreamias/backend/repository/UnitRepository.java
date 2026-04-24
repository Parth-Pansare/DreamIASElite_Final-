package com.dreamias.backend.repository;

import com.dreamias.backend.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    List<Unit> findBySubjectId(Long subjectId);
    Optional<Unit> findByNameAndSubjectId(String name, Long subjectId);
}
