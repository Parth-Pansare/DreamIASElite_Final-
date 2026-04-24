package com.dreamias.backend.controller;

import com.dreamias.backend.entity.*;
import com.dreamias.backend.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
public class ProgressController {

    private final ProgressService progressService;

    @Autowired
    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @PostMapping("/results")
    public ResponseEntity<TestResult> saveResult(@AuthenticationPrincipal User user, @RequestBody TestResult result) {
        return ResponseEntity.ok(progressService.saveResult(user, result));
    }

    @GetMapping("/results")
    public ResponseEntity<List<TestResult>> getMyResults(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(progressService.getUserResults(user.getId()));
    }

    @PostMapping("/bookmarks/{questionId}")
    public ResponseEntity<Bookmark> addBookmark(@AuthenticationPrincipal User user, @PathVariable Long questionId) {
        return ResponseEntity.ok(progressService.addBookmark(user, questionId));
    }

    @GetMapping("/bookmarks")
    public ResponseEntity<List<Bookmark>> getMyBookmarks(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(progressService.getUserBookmarks(user.getId()));
    }
}
