package com.spring.demo.core.entity;

import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class VideoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VIDEO_SEQ")
    @SequenceGenerator(name = "VIDEO_SEQ", initialValue = 1, allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false, insertable = true, unique = true)
    private Long id;

    @Column(name = "name", unique = true, updatable = false, insertable = true, nullable = false)
    private String name;

    @Column(name = "description", unique = false, updatable = true, insertable = true, nullable = true)
    private String description;

    public VideoEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VideoEntity)) {
            return false;
        }
        VideoEntity other = (VideoEntity) obj;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(description, other.description);
    }

    @Override
    public String toString() {
        return "VideoEntity{" + (id != null ? "id=" + id + ", " : "") + (name != null ? "name=" + name + ", " : "")
                        + (description != null ? "description=" + description : "") + "}";
    }
}
