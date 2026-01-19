package com.spring.demo.core.model;

import java.util.Objects;

public class Video {

    private final Integer id;

    private String name;

    public Video(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Video)) {
            return false;
        }
        Video other = (Video) obj;
        return Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return "Video{" + (id != null ? "id=" + id + ", " : "") + (name != null ? "name=" + name : "") + "}";
    }
}
