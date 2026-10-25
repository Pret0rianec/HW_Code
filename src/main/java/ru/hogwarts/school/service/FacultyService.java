package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {this.facultyRepository = facultyRepository;}

    public Faculty addFaculty(Faculty faculty) {
        if (faculty == null) {
            throw new IllegalArgumentException("Факультет не может быть null");
        }
        return facultyRepository.save(faculty);
    }

    public Faculty getByIdFaculty(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public Faculty updateFacul(Long id, Faculty updateFaculty) {
        if (!facultyRepository.existsById(id)) {
            System.out.println("Факультет с ID: " + id + " не найден.");
            return null;
        }
        updateFaculty.setId(id);
        return facultyRepository.save(updateFaculty);
    }

    public boolean removeByIdFaculty(Long id) {
        if (facultyRepository.existsById(id)) {
            facultyRepository.deleteById(id);
            System.out.println("Факультет с ID: " + id + " удалён.");
            return true;
        }
        return false;
    }

    public List<Faculty> filterByColor(String color) {
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> findByNameOrColor(String param) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(param, param);
    }
}
