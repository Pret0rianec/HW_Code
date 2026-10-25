package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return null;
    }

    public Student getByIdStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public Student updateStud(Long id, Student updateStudent) {
        if (!studentRepository.existsById(id)) {
            System.out.println("Студент с ID: " + id + " не найден.");
            return null;
        }
        updateStudent.setId(id);
        return studentRepository.save(updateStudent);
    }

    public boolean removeByIdStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            System.out.println("Студент с ID: " + id + " удалён.");
            return true;
        }
        return false;
    }

    public List<Student> filterByAge(int age) {
        return studentRepository.findById(age);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }
}
