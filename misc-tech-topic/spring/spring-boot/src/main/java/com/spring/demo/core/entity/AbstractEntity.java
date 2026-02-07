package com.spring.demo.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, updatable = false, insertable = true, unique = true)
    protected Long id;

    protected AbstractEntity(Long id) {
        this.id = id;
    }

    protected AbstractEntity() {
        // require a default safe constructor
    }

    public Long getId() {
        return id;
    }

    public AbstractEntity setId(Long id) {
        this.id = id;
        return this;
    }
}
