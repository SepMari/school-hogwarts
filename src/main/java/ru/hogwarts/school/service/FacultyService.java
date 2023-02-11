package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    //CRUD
    //Create (создание)
    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    //Read (чтение)
    public Faculty findFaculty(long id) {
        logger.debug("Was invoked method to find a faculty by id = {}", id);
        return facultyRepository.findById(id).get();
    }

    //Update (изменение)
    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method for edit faculty");
        return facultyRepository.save(faculty);
    }

    //Delete (удаление)
    public void removeFaculty(long id) {
        logger.debug("Was invoked method to remove a faculty by id = {}", id);
        facultyRepository.deleteById(id);
    }

    //фильтрация по цвету
    public Collection<Faculty> findByColor(String color) {
        logger.info("Was invoked method for find faculty by color");
        return facultyRepository.findByColorIgnoreCase(color);
    }

    //поиск по цвету или названию факультета
    public Collection<Faculty> findByNameOrColor(String name, String color) {
        logger.debug("Was invoked method to find a faculty by name = {} color = {}", name, color);
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    //студенты по id факультета
    public Collection<Student> findStudentsByFaculty(Long id) {
        logger.info("Was invoked method for find students by faculties");
        Optional<Faculty> faculty = findById(id);
        return faculty.map(Faculty::getStudent).orElse(null);
    }

    private Optional<Faculty> findById(long id) {
        return facultyRepository.findById(id);
    }

    public Collection<Faculty> getAllFaculty() {
        logger.info("Was invoked method for find all faculty");
        return facultyRepository.findAll();
    }

}
