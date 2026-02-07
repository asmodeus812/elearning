package com.spring.demo.core.entity;

import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "videos")
@SequenceGenerator(name = "VIDEOS_SEQ", initialValue = 1, allocationSize = 1)
public class VideoEntity extends AbstractAuditedEntity {

    @Column(name = "name", unique = true, updatable = true, insertable = true, nullable = false, length = 128)
    private String name;

    @Column(name = "description", unique = false, updatable = true, insertable = true, nullable = true, length = 1024)
    private String description;

    public VideoEntity() {
        // require a default safe constructor
    }

    public VideoEntity(Long id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }

    public VideoEntity(String name, String description) {
        this(null, name, description);
    }

    public String getName() {
        return name;
    }

    public VideoEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public VideoEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(name, description);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof VideoEntity)) {
            return false;
        }
        VideoEntity other = (VideoEntity) obj;
        return Objects.equals(name, other.name) && Objects.equals(description, other.description);
    }
}
