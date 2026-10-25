package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Collection;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findById(int age);

    Collection<Student> findByAgeBetween(int min, int max);
}
