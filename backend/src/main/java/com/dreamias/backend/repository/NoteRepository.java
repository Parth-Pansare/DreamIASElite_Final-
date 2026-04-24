package com.dreamias.backend.repository;

import com.dreamias.backend.entity.Note;
import com.dreamias.backend.entity.ContentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByTopicIdAndStatus(Long topicId, ContentStatus status);
    List<Note> findByStatus(ContentStatus status);
}
