package com.dreamias.backend.dto;

public class UnitDTO {
    private Long id;
    private String name;
    private Long subjectId;

    public UnitDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }
}
