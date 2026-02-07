package com.spring.demo.core.entity;

import java.util.Objects;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
@SequenceGenerator(name = "ROLES_SEQ", initialValue = 1, allocationSize = 50)
public class RoleEntity extends AbstractAuditedEntity {

    @Column(name = "name", unique = true, insertable = true, updatable = true, nullable = false, length = 32)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private Set<UserEntity> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_authority", joinColumns = @JoinColumn(name = "role_id"),
                    inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<AuthorityEntity> authorities;

    public RoleEntity() {
        // require a default safe constructor
    }

    public RoleEntity(Long id, String name, Set<AuthorityEntity> authorities) {
        super(id);
        this.name = name;
        this.authorities = authorities;
    }

    public RoleEntity(String name, Set<AuthorityEntity> authorities) {
        this(null, name, authorities);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(name);
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
        if (!(obj instanceof RoleEntity)) {
            return false;
        }
        RoleEntity other = (RoleEntity) obj;
        return Objects.equals(name, other.name);
    }
}
