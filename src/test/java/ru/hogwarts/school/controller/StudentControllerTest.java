package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/student";
    }

    @Test
    void testCreateStudent() {
        Student student = new Student();
        student.setName("Harry Potter");
        student.setAge(15);
        ResponseEntity<Student> response = restTemplate.postForEntity(getBaseUrl(), student, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Harry Potter");
    }

    @Test
    void testGetStudentById() {
        Student student = new Student();
        student.setName("Hermione Granger");
        student.setAge(16);
        Student created = restTemplate.postForObject(getBaseUrl(), student, Student.class);
        ResponseEntity<Student> response = restTemplate.getForEntity
                (getBaseUrl() + "/" + created.getId(), Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Hermione Granger");
    }

    @Test
    void testUpdateStudent() {
        Student student = new Student();
        student.setName("Ron Weasley");
        student.setAge(15);
        Student created = restTemplate.postForObject(getBaseUrl(), student, Student.class);
        created.setAge(17);
        HttpEntity<Student> requestEntity = new HttpEntity<>(created);
        ResponseEntity<Student> response = restTemplate.exchange(
                getBaseUrl() + "/" + created.getId(), HttpMethod.PUT, requestEntity, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getAge()).isEqualTo(17);
    }

    @Test
    void testDeleteStudent() {
        Student student = new Student();
        student.setName("Draco Malfoy");
        student.setAge(16);
        Student created = restTemplate.postForObject(getBaseUrl(), student, Student.class);
        restTemplate.delete(getBaseUrl() + "/" + created.getId());
        ResponseEntity<Student> response = restTemplate.getForEntity
                (getBaseUrl() + "/" + created.getId(), Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testGetStudentsByAgeBetween() {
        Student student = new Student();
        student.setName("Neville Longbottom");
        student.setAge(18);
        restTemplate.postForObject(getBaseUrl(), student, Student.class);
        String url = getBaseUrl() + "/age-between?min=17&max=19";
        ResponseEntity<Collection> response = restTemplate.getForEntity(url, Collection.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void testGetFacultyByStudentId() {
        Faculty faculty = new Faculty();
        faculty.setName("Gryffindor");
        faculty.setColor("Red");
        Faculty createdFaculty = restTemplate.postForObject
                ("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        Student student = new Student();
        student.setName("Ginny Weasley");
        student.setAge(14);
        student.setFaculty(createdFaculty);
        Student createdStudent = restTemplate.postForObject(getBaseUrl(), student, Student.class);
        ResponseEntity<Faculty> response = restTemplate.getForEntity
                (getBaseUrl() + "/" + createdStudent.getId() + "/faculty", Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Gryffindor");
    }
}
