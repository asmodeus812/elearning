package com.spring.demo.core.entity;

import java.util.List;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @SequenceGenerator(name = "USERS_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
    @Column(name = "id", nullable = false, updatable = false, insertable = true, unique = true)
    private Long id;

    @Column(name = "username", unique = true, updatable = false, insertable = true, nullable = false, length = 64)
    private String username;

    @Column(name = "password", unique = false, updatable = true, insertable = true, nullable = false, length = 128)
    private String password;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @Column(name = "role", unique = false, updatable = true, insertable = true, nullable = false)
    private RoleEntity role;

    public UserEntity() {
        // require a default safe constructor
    }

    public UserEntity(Long id, String name, String password) {
        this.id = id;
        this.username = name;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public String toString() {
        return "UserEntity{" + (id != null ? "id=" + id + ", " : "") + (username != null ? "username=" + username + ", " : "")
                        + (password != null ? "password=" + password + ", " : "") + (role != null ? "role=" + role : "") + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, role);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity) obj;
        return Objects.equals(id, other.id) && Objects.equals(username, other.username) && Objects.equals(password, other.password)
                        && Objects.equals(role, other.role);
    }
}
