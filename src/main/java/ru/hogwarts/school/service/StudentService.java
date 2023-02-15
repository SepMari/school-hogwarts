package ru.hogwarts.school.service;

import liquibase.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.hogwarts.school.model.AvatarStudent;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    //CRUD
    //Create (создание)
    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    //Read (чтение)
    public Student findStudent(long id) {
        logger.debug("Was invoked method to find a student by id = {}", id);
        return studentRepository.findById(id).get();
    }

    //Update (изменение)
    public Student editStudent(Student student) {
        logger.info("Was invoked method for edit student");
        return studentRepository.save(student);
    }

    //Delete (удаление)
    public void removeStudent(long id) {
        logger.debug("Was invoked method to remove a student by id = {}", id);
        studentRepository.deleteById(id);
    }

    //фильтрация студентов по возрасту
    public Collection<Student> findAge(Long age) {
        logger.info("Was invoked method for find student by age");
        return studentRepository.findByAge(age);
    }

    //поиск по возрасту между мин и макс
    public Collection<Student> findByAgeBetween(Long minAge, Long maxAge) {
        logger.info("Was invoked method for find student by age in interval min and max");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    //студенты по id факультета
    public Collection<Student> findStudentsByFaculty_Id(Long id) {
        logger.info("Was invoked method for find students by faculties");
        return studentRepository.findStudentsByFaculty_Id(id);
    }

    public Collection<Student> getAllStudent() {
        logger.info("Was invoked method for find all students");
        return studentRepository.findAll();
    }

    public Integer getCountStudents() {
        logger.info("Was invoked method for find all students in count");
        return studentRepository.getCount();
    }

    public String getAvgAgeStudents() {
        logger.info("Was invoked method for get average age students");
        Double result = studentRepository.findAll()
                .stream()
                .parallel()
                .mapToDouble(Student::getAge)
                .average().orElse(0);
        return String.format("%.1f", result);
    }

    public List<Student> getLastFive() {
        logger.info("Was invoked method for get last five students");
        return studentRepository.getLastFive();
    }

    public List<Student> findStudentStartA() {
        logger.info("Was invoked method for get student name starts with A");
        return studentRepository.findAll()
                .stream()
                .parallel()
                .filter(a -> StringUtils.startsWithIgnoreCase(a.getName(), "A"))
                .sorted(Comparator.comparing(Student::getName))
                .toList();
    }
}
