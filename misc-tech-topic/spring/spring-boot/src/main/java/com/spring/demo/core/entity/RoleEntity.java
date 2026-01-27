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
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @SequenceGenerator(name = "ROLES_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLES_SEQ")
    @Column(name = "id", nullable = false, updatable = false, insertable = true, unique = true)
    private Long id;

    @Column(name = "name", unique = true, insertable = true, updatable = true, nullable = false, length = 32)
    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, targetEntity = UserEntity.class)
    private List<UserEntity> users;

    @ManyToMany(mappedBy = "role", fetch = FetchType.LAZY, targetEntity = AuthorityEntity.class)
    private List<AuthorityEntity> authorities;

    public RoleEntity() {
        // require a default safe constructor
    }

    public RoleEntity(Long id, String name, List<AuthorityEntity> authorities) {
        this.id = id;
        this.name = name;
        this.authorities = authorities;
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

    public List<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "RoleEntity{" + (id != null ? "id=" + id + ", " : "") + (name != null ? "name=" + name + ", " : "")
                        + (authorities != null ? "authorities=" + authorities : "") + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, authorities);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RoleEntity)) {
            return false;
        }
        RoleEntity other = (RoleEntity) obj;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(authorities, other.authorities);
    }
}
