package com.dreamias.backend.service;

import com.dreamias.backend.dto.*;
import com.dreamias.backend.entity.*;
import com.dreamias.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContentService {

    private final SubjectRepository subjectRepository;
    private final UnitRepository unitRepository;
    private final TopicRepository topicRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public ContentService(SubjectRepository subjectRepository, 
                          UnitRepository unitRepository, 
                          TopicRepository topicRepository, 
                          QuestionRepository questionRepository) {
        this.subjectRepository = subjectRepository;
        this.unitRepository = unitRepository;
        this.topicRepository = topicRepository;
        this.questionRepository = questionRepository;
    }

    public List<SubjectDTO> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(this::mapToSubjectDTO)
                .collect(Collectors.toList());
    }

    public List<UnitDTO> getUnitsBySubject(Long subjectId) {
        return unitRepository.findBySubjectId(subjectId).stream()
                .map(this::mapToUnitDTO)
                .collect(Collectors.toList());
    }

    public List<TopicDTO> getTopicsByUnit(Long unitId) {
        return topicRepository.findByUnitId(unitId).stream()
                .map(this::mapToTopicDTO)
                .collect(Collectors.toList());
    }

    public List<QuestionDTO> getQuestionsByTopic(Long topicId) {
        return questionRepository.findByTopicId(topicId).stream()
                .map(this::mapToQuestionDTO)
                .collect(Collectors.toList());
    }

    /**
     * Batch ingestion for the Content Engine.
     */
    @Transactional
    public List<QuestionDTO> batchIngestQuestions(List<QuestionDTO> dtos) {
        return dtos.stream()
                .map(this::ingestQuestion)
                .collect(Collectors.toList());
    }

    @Transactional
    public QuestionDTO ingestQuestion(QuestionDTO dto) {
        // 1. Prevent Duplicates (Interview Tip: Idempotency)
        Optional<Question> existing = questionRepository.findByPrompt(dto.getPrompt());
        if (existing.isPresent()) {
            return mapToQuestionDTO(existing.get());
        }

        // 2. Find or Create Topic using the hierarchy
        Topic topic;
        if (dto.getTopicId() != null) {
            topic = topicRepository.findById(dto.getTopicId())
                    .orElseThrow(() -> new RuntimeException("Topic ID not found"));
        } else {
            topic = findOrCreateHierarchy(dto.getSubjectName(), dto.getUnitName(), dto.getTopicName());
        }

        // 3. Save Question
        Question question = new Question();
        question.setPrompt(dto.getPrompt());
        question.setOptions(dto.getOptions());
        question.setCorrectIndex(dto.getCorrectIndex());
        question.setExplanation(dto.getExplanation());
        question.setTopic(topic);

        return mapToQuestionDTO(questionRepository.save(question));
    }

    /**
     * Helper to handle the dynamic creation of Subjects, Units, and Topics.
     */
    private Topic findOrCreateHierarchy(String sName, String uName, String tName) {
        // Find or Create Subject
        Subject subject = subjectRepository.findByName(sName)
                .orElseGet(() -> {
                    Subject s = new Subject();
                    s.setName(sName);
                    return subjectRepository.save(s);
                });

        // Find or Create Unit
        Unit unit = unitRepository.findByNameAndSubjectId(uName, subject.getId())
                .orElseGet(() -> {
                    Unit u = new Unit();
                    u.setName(uName);
                    u.setSubject(subject);
                    return unitRepository.save(u);
                });

        // Find or Create Topic
        return topicRepository.findByNameAndUnitId(tName, unit.getId())
                .orElseGet(() -> {
                    Topic t = new Topic();
                    t.setName(tName);
                    t.setUnit(unit);
                    return topicRepository.save(t);
                });
    }

    private SubjectDTO mapToSubjectDTO(Subject subject) {
        SubjectDTO dto = new SubjectDTO();
        dto.setId(subject.getId());
        dto.setName(subject.getName());
        dto.setDescription(subject.getDescription());
        return dto;
    }

    private UnitDTO mapToUnitDTO(Unit unit) {
        UnitDTO dto = new UnitDTO();
        dto.setId(unit.getId());
        dto.setName(unit.getName());
        dto.setSubjectId(unit.getSubject().getId());
        return dto;
    }

    private TopicDTO mapToTopicDTO(Topic topic) {
        TopicDTO dto = new TopicDTO();
        dto.setId(topic.getId());
        dto.setName(topic.getName());
        dto.setUnitId(topic.getUnit().getId());
        return dto;
    }

    private QuestionDTO mapToQuestionDTO(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setPrompt(question.getPrompt());
        dto.setOptions(question.getOptions());
        dto.setCorrectIndex(question.getCorrectIndex());
        dto.setExplanation(question.getExplanation());
        dto.setTopicId(question.getTopic().getId());
        
        // Include names in DTO for confirmation
        dto.setTopicName(question.getTopic().getName());
        dto.setUnitName(question.getTopic().getUnit().getName());
        dto.setSubjectName(question.getTopic().getUnit().getSubject().getName());
        
        if (question.getPaper() != null) {
            dto.setPaperId(question.getPaper().getId());
        }
        return dto;
    }
}
