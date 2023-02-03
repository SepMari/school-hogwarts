package ru.hogwarts.school.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
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

    //CRUD
    //Create (создание)
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    //Read (чтение)
    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }

    //Update (изменение)
    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    //Delete (удаление)
    public void removeStudent(long id) {
        studentRepository.deleteById(id);
    }

    //фильтрация студентов по возрасту
    public Collection<Student> findAge(Long age) {
        return studentRepository.findByAge(age);
    }

    //поиск по возрасту между мин и макс
    public Collection<Student> findByAgeBetween(Long minAge, Long maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    //студенты по id факультета
    public Collection<Student> findStudentsByFaculty_Id(Long id) {
        return studentRepository.findStudentsByFaculty_Id(id);
    }

    public Collection<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public Integer getCountStudents() {
        return studentRepository.getCount();
    }

    public Integer getAvgAgeStudents() {
        return studentRepository.getAvgAgeStudents();
    }

    public List<Student> getLastFive() {
        return studentRepository.getLastFive();
    }

}
