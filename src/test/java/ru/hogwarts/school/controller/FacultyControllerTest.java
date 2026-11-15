package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class FacultyControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/faculty";
    }

    @Test
    void testCreateFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Slytherin");
        faculty.setColor("Green");
        ResponseEntity<Faculty> response = restTemplate.postForEntity(getBaseUrl(), faculty, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Slytherin");
    }

    @Test
    void testGetFacultyById() {
        Faculty faculty = new Faculty();
        faculty.setName("Ravenclaw");
        faculty.setColor("Blue");
        Faculty created = restTemplate.postForObject(getBaseUrl(), faculty, Faculty.class);
        ResponseEntity<Faculty> response = restTemplate.getForEntity
                (getBaseUrl() + "/" + created.getId(), Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getColor()).isEqualTo("Blue");
    }

    @Test
    void testUpdateFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Hufflepuff");
        faculty.setColor("Yellow");
        Faculty created = restTemplate.postForObject(getBaseUrl(), faculty, Faculty.class);
        created.setColor("Black and Yellow");
        HttpEntity<Faculty> requestEntity = new HttpEntity<>(created);
        ResponseEntity<Faculty> response = restTemplate.exchange(
                getBaseUrl() + "/" + created.getId(), HttpMethod.PUT, requestEntity, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getColor()).isEqualTo("Black and Yellow");
    }

    @Test
    void testDeleteFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Durmstrang");
        faculty.setColor("Brown");
        Faculty created = restTemplate.postForObject(getBaseUrl(), faculty, Faculty.class);
        restTemplate.delete(getBaseUrl() + "/" + created.getId());
        ResponseEntity<Faculty> response = restTemplate.getForEntity
                (getBaseUrl() + "/" + created.getId(), Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testFindFacultiesByNameOrColor() {
        Faculty faculty = new Faculty();
        faculty.setName("Beauxbatons");
        faculty.setColor("LightBlue");
        restTemplate.postForObject(getBaseUrl(), faculty, Faculty.class);
        String url = getBaseUrl() + "/search?param=lightblue";
        ResponseEntity<Collection> response = restTemplate.getForEntity(url, Collection.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void testGetStudentsByFacultyId() {
        Faculty faculty = new Faculty();
        faculty.setName("Gryffindor Test");
        faculty.setColor("Scarlet");
        Faculty createdFaculty = restTemplate.postForObject(getBaseUrl(), faculty, Faculty.class);
        Student student = new Student();
        student.setName("Fred Weasley");
        student.setAge(17);
        student.setFaculty(createdFaculty);
        restTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);
        ResponseEntity<List<Student>> response = restTemplate.exchange(
                getBaseUrl() + "/" + createdFaculty.getId() + "/students", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Student>>() {});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody().get(0).getName()).isEqualTo("Fred Weasley");
    }
}
