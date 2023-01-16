package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;


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

    public Collection<Faculty> findColor(String color) {
        ArrayList<Faculty> result = new ArrayList<>();
        for (Faculty faculty : facultyRepository.findAll()) {
            if (Objects.equals(faculty.getColor(), color)) {
                result.add(faculty);
            }
        }
        return result;
    }
}
