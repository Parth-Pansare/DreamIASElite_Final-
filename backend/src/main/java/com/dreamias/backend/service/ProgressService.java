package com.dreamias.backend.service;

import com.dreamias.backend.entity.*;
import com.dreamias.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProgressService {

    private final TestResultRepository testResultRepository;
    private final BookmarkRepository bookmarkRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public ProgressService(TestResultRepository testResultRepository, 
                           BookmarkRepository bookmarkRepository,
                           QuestionRepository questionRepository) {
        this.testResultRepository = testResultRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.questionRepository = questionRepository;
    }

    public TestResult saveResult(User user, TestResult result) {
        result.setUser(user);
        return testResultRepository.save(result);
    }

    public List<TestResult> getUserResults(Long userId) {
        return testResultRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Bookmark addBookmark(User user, Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        
        Bookmark bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setQuestion(question);
        return bookmarkRepository.save(bookmark);
    }

    public List<Bookmark> getUserBookmarks(Long userId) {
        return bookmarkRepository.findByUserId(userId);
    }
}
