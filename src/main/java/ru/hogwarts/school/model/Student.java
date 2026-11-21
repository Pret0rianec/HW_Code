package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_student")
    private Long id;

    private String name;
    private int age;


    @ManyToOne
    @JoinColumn(name = "faculty")
    private Faculty faculty;

    @OneToOne(mappedBy = "student")
    @JsonIgnore
    private Avatar avatar;

    public Student() {}

    public Student(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public void setFaculty(Faculty faculty) {this.faculty = faculty;}

    public Faculty getFaculty() {return faculty;}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Avatar getAvatar() {return avatar;}

    public void setAvatar(Avatar avatar) {this.avatar = avatar;}
}
