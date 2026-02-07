package com.spring.demo.core.entity;

import java.util.Objects;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "authorities")
@SequenceGenerator(name = "AUTH_SEQ", initialValue = 1, allocationSize = 1)
public class AuthorityEntity extends AbstractAuditedEntity {

    @Column(name = "name", nullable = false, updatable = false, insertable = true, unique = true, length = 32)
    private String name;

    @Column(name = "grant", nullable = false, updatable = false, insertable = true, unique = true, length = 128)
    private String grant;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authorities")
    private Set<RoleEntity> roles;

    public AuthorityEntity() {
        // require a default safe constructor
    }

    public AuthorityEntity(Long id, String name, String grant) {
        super(id);
        this.name = name;
        this.grant = grant;
    }

    public AuthorityEntity(String name, String grant) {
        this(null, name, grant);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public String getGrant() {
        return grant;
    }

    public void setGrant(String grant) {
        this.grant = grant;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(name, grant);
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
        if (!(obj instanceof AuthorityEntity)) {
            return false;
        }
        AuthorityEntity other = (AuthorityEntity) obj;
        return Objects.equals(name, other.name) && Objects.equals(grant, other.grant);
    }
}
