package com.dreamias.backend.dto;

public class TopicDTO {
    private Long id;
    private String name;
    private Long unitId;

    public TopicDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getUnitId() { return unitId; }
    public void setUnitId(Long unitId) { this.unitId = unitId; }
}
