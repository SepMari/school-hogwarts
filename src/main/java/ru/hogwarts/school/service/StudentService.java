package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

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
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            if (Objects.equals(student.getAge(), age)) {
                result.add(student);
            }
        }
        return result;
    }

    //поиск по возрасту между мин и макс
    public Collection<Student> findByAgeBetween(Long minAge, Long maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    //поиск студентов по факультету
    public Collection<Student> findStudentsByFaculty (Long faculty_id) {
        return studentRepository.findStudentsByFaculty_Id(faculty_id);
    }

    public Collection<Student> getAllStudent() {
        return studentRepository.findAll();
    }
}
