package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private StudentService studentService;
    @MockBean
    private StudentRepository studentRepository;

    @Test
    public void testCreateStudent() throws Exception {
        Student student = new Student(1L, "Harry Potter", 11);
        when(studentService.addStudent(any(Student.class))).thenReturn(student);
        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Harry Potter"))
                .andExpect(jsonPath("$.age").value(11));
    }

    @Test
    public void testGetByIdStudent() throws Exception {
        Student student = new Student(1L, "Harry Potter", 11);
        when(studentService.getByIdStudent(1L)).thenReturn(student);
        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Harry Potter"));
    }

    @Test
    public void testEditStudent() throws Exception {
        Student updateStudent = new Student(1L, "Harry Potter", 12);
        when(studentService.updateStud(eq(1L), any(Student.class))).thenReturn(updateStudent);
        mockMvc.perform(put("/student/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(12));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        when(studentService.removeByIdStudent(1L)).thenReturn(true);
        mockMvc.perform(delete("/student/1")).andExpect(status().isOk());
    }

    @Test
    public void testGetStudentByAge() throws Exception {
        Student s1 = new Student(1L, "Harry", 11);
        Student s2 = new Student(2L, "Ron", 11);
        when(studentService.filterByAge(11)).thenReturn(List.of(s1, s2));
        mockMvc.perform(get("/student/filter").param("age", "11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.[0].name").value("Harry"));
    }

    @Test
    public void testGetStudentsByAgeBetween() throws Exception {
        Student s1 = new Student(1L, "Harry", 11);
        Student s2 = new Student(2L, "Hermione", 12);
        when(studentService.findByAgeBetween(10, 13)).thenReturn(List.of(s1, s2));
        mockMvc.perform(get("/student/age-between")
                        .param("min", "10")
                        .param("max", "13"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetFacultyOfStudent() throws Exception {
        Faculty faculty = new Faculty(1L, "Gryffindor", "Red");
        Student student = new Student(1L, "Harry Potter", 12);
        student.setFaculty(faculty);
        when(studentService.getByIdStudent(1L)).thenReturn(student);
        mockMvc.perform(get("/student/1/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gryffindor"))
                .andExpect(jsonPath("$.color").value("Red"));
    }
}
