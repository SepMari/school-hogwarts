package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByAgeBetween(Long minAge, Long maxAge);

    Collection<Student> findByAge(Long age);

    Collection<Student> findStudentsByFaculty_Id (Long id);
}
