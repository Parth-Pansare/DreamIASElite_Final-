package com.dreamias.backend.repository;

import com.dreamias.backend.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByUnitId(Long unitId);
    Optional<Topic> findByNameAndUnitId(String name, Long unitId);
}
