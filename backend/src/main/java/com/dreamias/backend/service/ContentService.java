package com.dreamias.backend.service;

import com.dreamias.backend.dto.*;
import com.dreamias.backend.entity.*;
import com.dreamias.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContentService {

    private final SubjectRepository subjectRepository;
    private final UnitRepository unitRepository;
    private final TopicRepository topicRepository;
    private final QuestionRepository questionRepository;
    private final NoteRepository noteRepository;

    @Autowired
    public ContentService(SubjectRepository subjectRepository, 
                          UnitRepository unitRepository, 
                          TopicRepository topicRepository, 
                          QuestionRepository questionRepository,
                          NoteRepository noteRepository) {
        this.subjectRepository = subjectRepository;
        this.unitRepository = unitRepository;
        this.topicRepository = topicRepository;
        this.questionRepository = questionRepository;
        this.noteRepository = noteRepository;
    }

    // --- Public Content (Students see only APPROVED) ---

    public List<SubjectDTO> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(this::mapToSubjectDTO)
                .collect(Collectors.toList());
    }

    public List<QuestionDTO> getQuestionsByTopic(Long topicId) {
        return questionRepository.findByTopicIdAndStatus(topicId, ContentStatus.APPROVED).stream()
                .map(this::mapToQuestionDTO)
                .collect(Collectors.toList());
    }

    public List<Note> getNotesByTopic(Long topicId) {
        return noteRepository.findByTopicIdAndStatus(topicId, ContentStatus.APPROVED);
    }

    // --- Admin Ingestion ---

    @Transactional
    public QuestionDTO ingestQuestion(QuestionDTO dto) {
        Topic topic = findOrCreateHierarchy(dto.getSubjectName(), dto.getUnitName(), dto.getTopicName());

        Question question = new Question();
        question.setPrompt(dto.getPrompt());
        question.setOptions(dto.getOptions());
        question.setCorrectIndex(dto.getCorrectIndex());
        question.setExplanation(dto.getExplanation());
        question.setTopic(topic);
        question.setStatus(ContentStatus.PENDING); // Force pending for review

        return mapToQuestionDTO(questionRepository.save(question));
    }

    private Topic findOrCreateHierarchy(String sName, String uName, String tName) {
        Subject subject = subjectRepository.findByName(sName)
                .orElseGet(() -> {
                    Subject s = new Subject();
                    s.setName(sName);
                    return subjectRepository.save(s);
                });

        Unit unit = unitRepository.findByNameAndSubjectId(uName, subject.getId())
                .orElseGet(() -> {
                    Unit u = new Unit();
                    u.setName(uName);
                    u.setSubject(subject);
                    return unitRepository.save(u);
                });

        return topicRepository.findByNameAndUnitId(tName, unit.getId())
                .orElseGet(() -> {
                    Topic t = new Topic();
                    t.setName(tName);
                    t.setUnit(unit);
                    return topicRepository.save(t);
                });
    }

    // Mapping methods...
    private SubjectDTO mapToSubjectDTO(Subject subject) {
        SubjectDTO dto = new SubjectDTO();
        dto.setId(subject.getId());
        dto.setName(subject.getName());
        dto.setDescription(subject.getDescription());
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
        return dto;
    }
}
