package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Collection;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findById(int age);
    Collection<Student> findByAgeBetween(int min, int max);

    @Query(value = "Select count(*) from student", nativeQuery = true)
    Integer getTotalCountOfStudents();

    @Query(value = "Select AVG(age) from student", nativeQuery = true)
    Double getAverageAgeOfStudents();

    @Query(value = "Select * from student order by id_student desc limit 5", nativeQuery = true)
    List<Student> getLastFiveStudents();
}
