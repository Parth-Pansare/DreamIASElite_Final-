package com.dreamias.backend.service;

import com.dreamias.backend.entity.*;
import com.dreamias.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    private final QuestionRepository questionRepository;
    private final NoteRepository noteRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public ReviewService(QuestionRepository questionRepository, 
                         NoteRepository noteRepository, 
                         ArticleRepository articleRepository) {
        this.questionRepository = questionRepository;
        this.noteRepository = noteRepository;
        this.articleRepository = articleRepository;
    }

    public Map<String, List<?>> getAllPendingContent() {
        Map<String, List<?>> pending = new HashMap<>();
        pending.put("questions", questionRepository.findByStatus(ContentStatus.PENDING));
        pending.put("notes", noteRepository.findByStatus(ContentStatus.PENDING));
        pending.put("articles", articleRepository.findByStatusOrderByPublishedAtDesc(ContentStatus.PENDING));
        return pending;
    }

    @Transactional
    public void approveQuestion(Long id) {
        Question q = questionRepository.findById(id).orElseThrow();
        q.setStatus(ContentStatus.APPROVED);
        questionRepository.save(q);
    }

    @Transactional
    public void approveNote(Long id) {
        Note n = noteRepository.findById(id).orElseThrow();
        n.setStatus(ContentStatus.APPROVED);
        noteRepository.save(n);
    }

    @Transactional
    public void approveArticle(Long id) {
        Article a = articleRepository.findById(id).orElseThrow();
        a.setStatus(ContentStatus.APPROVED);
        articleRepository.save(a);
    }
}
