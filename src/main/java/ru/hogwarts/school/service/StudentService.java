package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Service
public class StudentService {

    private final HashMap<Long, Student> students = new HashMap<>();
    private long id = 0;

    //CRUD
    //Create (создание)
    public Student createStudent(Student student) {
        student.setId(++id);
        students.put(id, student);
        return student;
    }

    //Read (чтение)
    public Student findStudent(long id) {
        return students.get(id);
    }

    //Update (изменение)
    public Student editStudent(Student student) {
        students.put(student.getId(), student);
        return student;
    }

    //Delete (удаление)
    public Student removeStudent(long id) {
        return students.remove(id);
    }

    public Collection<Student> findAge(int age) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.getAge() == age) {
                result.add(student);
            }
        }
        return result;
    }
}
