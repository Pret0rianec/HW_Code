package ru.hogwarts.school.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_faculty")
    private Long id;

    private String name;
    private String color;

    @OneToMany(mappedBy = "faculty")
    private Set<Student> students;

    public Faculty() {}

    public Faculty(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Set<Student> getStudents() {return students;}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
