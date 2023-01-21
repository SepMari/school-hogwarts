package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.removeStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> findStudents(@RequestParam(required = false) Long age,
                                                            @RequestParam(required = false) Long minAge,
                                                            @RequestParam(required = false) Long maxAge) {
        if (minAge != null && maxAge != null) {
            return ResponseEntity.ok(studentService.findByAgeBetween(minAge, maxAge));
        }
        if (age != null) {
            return ResponseEntity.ok(studentService.findAge(age));
        }
        return ResponseEntity.ok(studentService.getAllStudent());
    }


    @GetMapping(path = "faculty/{faculty_id}")
    public ResponseEntity<Collection<Student>> findStudentsByFaculty(@PathVariable Long faculty_id) {
        Collection<Student> students = studentService.findStudentsByFaculty(faculty_id);
        if (faculty_id == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

}
