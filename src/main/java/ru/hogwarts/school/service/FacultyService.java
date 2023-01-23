package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    //CRUD
    //Create (создание)
    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    //Read (чтение)
    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).get();
    }

    //Update (изменение)
    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    //Delete (удаление)
    public void removeFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    //фильтрация по цвету
    public Collection<Faculty> findByColor(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }

    //поиск по цвету или названию факультета
    public Collection<Faculty> findByNameOrColor(String name, String color) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    //студенты по id факультета
    public Collection<Student> findStudentsByFaculty(Long id) {
        Optional<Faculty> faculty = findById(id);
        return faculty.map(Faculty::getStudent).orElse(null);
    }

    public Optional<Faculty> findById(long id) {
        return facultyRepository.findById(id);
    }

    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

}
