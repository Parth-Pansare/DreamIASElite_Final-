package com.dreamias.backend.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "pyq_papers")
public class PyqPaper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "paper", fetch = FetchType.LAZY)
    private List<Question> questions;

    public PyqPaper() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }
}
