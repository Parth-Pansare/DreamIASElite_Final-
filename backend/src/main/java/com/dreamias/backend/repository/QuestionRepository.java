package com.dreamias.backend.repository;

import com.dreamias.backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByTopicIdAndStatus(Long topicId, com.dreamias.backend.entity.ContentStatus status);
    List<Question> findByStatus(com.dreamias.backend.entity.ContentStatus status);
}
