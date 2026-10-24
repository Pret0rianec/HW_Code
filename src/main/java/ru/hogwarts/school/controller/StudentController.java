package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService,
                             StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student savedStudent = studentService.addStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> readByIdStudent(@PathVariable Long id) {
        Student student = studentService.getByIdStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<List<Student>> readAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudent());
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        Student updatedStudent = studentService.updateStud(id, student);
        if (updatedStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        boolean deletedStudent = studentService.removeByIdStudent(id);
        if (!deletedStudent) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("filter")
    public ResponseEntity<List<Student>> filterStudentsByAge(@RequestParam int age) {
        List<Student> filteredStudents = studentService.filterByAge(age);
        return ResponseEntity.ok(filteredStudents);
    }

    @GetMapping("age-between")
    public ResponseEntity<Collection<Student>> getStudentsByAgeBetween(
            @RequestParam int min, @RequestParam int max) {
        Collection<Student> students = studentService.findByAgeBetween(min, max);
        return ResponseEntity.ok(students);
    }

    @GetMapping("{id}/faculty")
    public ResponseEntity<Faculty> getFacultyByStudentId(@PathVariable Long id) {
        Faculty faculty = studentService.getByIdStudent(id).getFaculty();
        return ResponseEntity.ok(faculty);
    }
}

