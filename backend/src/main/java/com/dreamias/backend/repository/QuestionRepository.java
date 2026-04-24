package com.dreamias.backend.repository;

import com.dreamias.backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByTopicId(Long topicId);
    List<Question> findByPaperId(Long paperId);
    Optional<Question> findByPrompt(String prompt);
}
