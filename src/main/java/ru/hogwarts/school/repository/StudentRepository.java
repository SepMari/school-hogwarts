package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByAgeBetween(Long minAge, Long maxAge);

    Collection<Student> findByAge(Long age);

    Collection<Student> findStudentsByFaculty_Id (Long id);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    int getCount();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    int getAvgAgeStudents();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFive();
}
