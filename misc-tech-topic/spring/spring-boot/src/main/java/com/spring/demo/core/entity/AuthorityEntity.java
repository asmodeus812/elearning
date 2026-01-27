package com.spring.demo.core.entity;

import java.util.List;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "authorities")
public class AuthorityEntity {

    @Id
    @SequenceGenerator(name = "AUTH_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTH_SEQ")
    @Column(name = "id", nullable = false, updatable = false, insertable = true, unique = true)
    private Long id;

    @Column(name = "name", nullable = false, updatable = false, insertable = true, unique = true, length = 32)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @Column(name = "role", updatable = true, insertable = true, unique = false)
    private List<RoleEntity> roles;

    public AuthorityEntity() {
        // require a default safe constructor
    }

    public AuthorityEntity(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "AuthorityEntity{" + (id != null ? "id=" + id + ", " : "") + (name != null ? "name=" + name + ", " : "")
                        + (roles != null ? "roles=" + roles : "") + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, roles);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AuthorityEntity)) {
            return false;
        }
        AuthorityEntity other = (AuthorityEntity) obj;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(roles, other.roles);
    }
}
