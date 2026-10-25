package ru.hogwarts.school.controller;

import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty savedFaculty = facultyService.addFaculty(faculty);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFaculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> readByIdFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.getByIdFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping
    public ResponseEntity<List<Faculty>> readAllFaculty() {
        return ResponseEntity.ok(facultyService.getAllFaculty());
    }

    @PutMapping("{id}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Long id, @RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.updateFacul(id, faculty);
        if (updatedFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        boolean deletedFaculty = facultyService.removeByIdFaculty(id);
        if (!deletedFaculty) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("filter")
    public ResponseEntity<List<Faculty>> filterFacultiesByColor(@RequestParam String color) {
        List<Faculty> filteredFaculties = facultyService.filterByColor(color);
        return ResponseEntity.ok(filteredFaculties);
    }

    @GetMapping("search")
    public ResponseEntity<Collection<Faculty>> findFacultyByNameOrColor(
            @RequestParam("param") String param) {
        Collection<Faculty> faculties = facultyService.findByNameOrColor(param);
        return ResponseEntity.ok(faculties);
    }
    @GetMapping("{id}/students")
    public ResponseEntity<Collection<Student>> getStudentsByFacultyId(@PathVariable Long id){
        Collection<Student> students = facultyService.getByIdFaculty(id).getStudents();
        return ResponseEntity.ok(students);
    }
}
