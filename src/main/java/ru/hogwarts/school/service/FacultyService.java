package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;


@Service
public class FacultyService {

    private final HashMap<Long, Faculty> faculties = new HashMap();
    private long id = 0;

    //CRUD
    //Create (создание)
    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++id);
        faculties.put(id, faculty);
        return faculty;
    }

    //Read (чтение)
    public Faculty findFaculty(long id) {
        return faculties.get(id);
    }

    //Update (изменение)
    public Faculty editFaculty(Faculty faculty) {
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    //Delete (удаление)
    public Faculty removeFaculty(long id) {
        return faculties.remove(id);
    }

    public Collection<Faculty> findColor(String color) {
        ArrayList<Faculty> result = new ArrayList<>();
        for (Faculty faculty : faculties.values()) {
            if (Objects.equals(faculty.getColor(), color)) {
                result.add(faculty);
            }
        }
        return result;
    }
}
