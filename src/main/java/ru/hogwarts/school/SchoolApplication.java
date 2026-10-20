package ru.hogwarts.school;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

@SpringBootApplication
public class SchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner demo(StudentService service) {
//        return args -> {
//            System.out.println("\n=== НАЧАЛО ТЕСТИРОВАНИЯ CRUD ===\n");
//
//            // 1. CREATE: Создаем студентов (метод должен возвращать объект Student)
//            Student s1 = service.addStudent(new Student(0L, "Иван", 20));
//            Student s2 = service.addStudent(new Student(0L, "Мария", 22));
//
//            System.out.println("Создан студент с ID: " + s1.getIdStudent());
//
//            // 2. READ: Читаем данные
//            Student found = service.getByIdStudent(1L);
//            System.out.println("Найдено имя: " + found.getNameStudent());
//
//            // 3. UPDATE: Изменяем данные студента с ID 1
//            Student newData = new Student(0L, "Иван Измененный", 21);
//            service.updateStud(1L, newData);
//
//            // Обратите внимание: у вас в примере использовались два разных имени метода:
//            // getByIdStudent и readByIdStudent. Используем один правильный:
//            System.out.println("Имя после UPDATE: " + service.getByIdStudent(1L).getNameStudent());
//
//            // 4. DELETE: Удаляем студента с ID 2
//            boolean isDeleted = service.removeByIdStudent(2L);
//            System.out.println("Удален успешно? " + isDeleted);
//            System.out.println("Всего студентов осталось: " + service.getAllStudent().size());
//
//            System.out.println("\n=== КОНЕЦ ТЕСТИРОВАНИЯ ===\n");
//        };
//    }
}
