package com.dreamias.backend.dto;

import java.util.List;

public class QuestionDTO {
    private Long id;
    private String prompt;
    private List<String> options;
    private Integer correctIndex;
    private String explanation;
    
    // For direct linking via ID (if known)
    private Long topicId;
    private Long paperId;

    // For automated ingestion via Name (The "AI Parser" flow)
    private String subjectName;
    private String unitName;
    private String topicName;

    public QuestionDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }

    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }

    public Integer getCorrectIndex() { return correctIndex; }
    public void setCorrectIndex(Integer correctIndex) { this.correctIndex = correctIndex; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public Long getTopicId() { return topicId; }
    public void setTopicId(Long topicId) { this.topicId = topicId; }

    public Long getPaperId() { return paperId; }
    public void setPaperId(Long paperId) { this.paperId = paperId; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getUnitName() { return unitName; }
    public void setUnitName(String unitName) { this.unitName = unitName; }

    public String getTopicName() { return topicName; }
    public void setTopicName(String topicName) { this.topicName = topicName; }
}
