package com.dreamias.backend.controller;

import com.dreamias.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/pending")
    public ResponseEntity<Map<String, List<?>>> getPendingContent() {
        return ResponseEntity.ok(reviewService.getAllPendingContent());
    }

    @PostMapping("/approve/question/{id}")
    public ResponseEntity<Void> approveQuestion(@PathVariable Long id) {
        reviewService.approveQuestion(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/approve/note/{id}")
    public ResponseEntity<Void> approveNote(@PathVariable Long id) {
        reviewService.approveNote(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/approve/article/{id}")
    public ResponseEntity<Void> approveArticle(@PathVariable Long id) {
        reviewService.approveArticle(id);
        return ResponseEntity.ok().build();
    }
}
