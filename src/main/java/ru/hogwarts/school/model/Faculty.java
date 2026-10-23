package ru.hogwarts.school.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idFaculty;

    private String nameFaculty;
    private String colorFaculty;

    public Faculty() {}

    public Faculty(Long idFaculty, String nameFaculty, String colorFaculty) {
        this.idFaculty = idFaculty;
        this.nameFaculty = nameFaculty;
        this.colorFaculty = colorFaculty;
    }

    public Long getIdFaculty() {
        return idFaculty;
    }

    public String getNameFaculty() {
        return nameFaculty;
    }

    public String getColorFaculty() {
        return colorFaculty;
    }

    public void setIdFaculty(Long id) {
        this.idFaculty = id;
    }

    public void setNameFaculty(String name) {
        this.nameFaculty = name;
    }

    public void setColorFaculty(String color) {
        this.colorFaculty = color;
    }

}
