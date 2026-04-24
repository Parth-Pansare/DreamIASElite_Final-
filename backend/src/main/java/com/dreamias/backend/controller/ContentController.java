package com.dreamias.backend.controller;

import com.dreamias.backend.dto.*;
import com.dreamias.backend.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {

    private final ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectDTO>> getAllSubjects() {
        return ResponseEntity.ok(contentService.getAllSubjects());
    }

    @GetMapping("/units/{subjectId}")
    public ResponseEntity<List<UnitDTO>> getUnitsBySubject(@PathVariable Long subjectId) {
        return ResponseEntity.ok(contentService.getUnitsBySubject(subjectId));
    }

    @GetMapping("/topics/{unitId}")
    public ResponseEntity<List<TopicDTO>> getTopicsByUnit(@PathVariable Long unitId) {
        return ResponseEntity.ok(contentService.getTopicsByUnit(unitId));
    }

    @GetMapping("/questions/{topicId}")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByTopic(@PathVariable Long topicId) {
        return ResponseEntity.ok(contentService.getQuestionsByTopic(topicId));
    }

    @PostMapping("/admin/ingest")
    public ResponseEntity<QuestionDTO> ingestQuestion(@RequestBody QuestionDTO questionDTO) {
        return ResponseEntity.ok(contentService.ingestQuestion(questionDTO));
    }

    @PostMapping("/admin/batch-ingest")
    public ResponseEntity<List<QuestionDTO>> batchIngestQuestions(@RequestBody List<QuestionDTO> questionDTOs) {
        return ResponseEntity.ok(contentService.batchIngestQuestions(questionDTOs));
    }
}
