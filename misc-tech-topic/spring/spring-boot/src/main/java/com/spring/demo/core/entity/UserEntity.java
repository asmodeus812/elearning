package com.spring.demo.core.entity;

import java.util.Objects;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
@SequenceGenerator(name = "USERS_SEQ", initialValue = 1, allocationSize = 50)
public class UserEntity extends AbstractAuditedEntity {

    @Column(name = "username", unique = true, updatable = false, insertable = true, nullable = false, length = 64)
    private String username;

    @Column(name = "password", unique = false, updatable = true, insertable = true, nullable = false, length = 128)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id", nullable = false, insertable = true, updatable = true)
    private RoleEntity role;

    public UserEntity() {
        // require a default safe constructor
    }

    public UserEntity(String name, String password, RoleEntity role) {
        this(null, name, password, role);
    }

    public UserEntity(Long id, String name, String password, RoleEntity role) {
        super(id);
        this.username = name;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(username);
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
        if (!(obj instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity) obj;
        return Objects.equals(username, other.username);
    }
}
