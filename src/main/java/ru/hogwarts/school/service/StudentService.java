package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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

    public Collection<Student> findAge(int age) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            if (student.getAge() == age) {
                result.add(student);
            }
        }
        return result;
    }
}
