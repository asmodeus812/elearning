package com.spring.demo.core.entity;

import java.util.Objects;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractAuditedEntity extends AbstractEntity {

    protected AbstractAuditedEntity() {
        // require a default safe constructor
    }

    protected AbstractAuditedEntity(Long id) {
        super(id);
    }

    @Override
    public AbstractAuditedEntity setId(Long id) {
        super.setId(id);
        return this;
    }

    @Override
    public String toString() {
        return "AbstractAuditedEntity{}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AbstractAuditedEntity)) {
            return false;
        }
        AbstractAuditedEntity other = (AbstractAuditedEntity) obj;
        return Objects.equals(id, other.id);
    }
}
