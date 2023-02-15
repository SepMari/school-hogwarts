package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.AvatarStudent;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarStudentService;
import ru.hogwarts.school.service.StudentService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;
    private final AvatarStudentService avatarStudentService;

    public StudentController(StudentService studentService, AvatarStudentService avatarStudentService) {
        this.studentService = studentService;
        this.avatarStudentService = avatarStudentService;
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


    //студенты по id факультета
    @GetMapping(path = "faculty/{id}")
    public ResponseEntity<Collection<Student>> findStudentsByFaculty_Id(@PathVariable Long id) {
        Collection<Student> student = studentService.findStudentsByFaculty_Id(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping (value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() > 1024 * 300) {
            return ResponseEntity.badRequest().body("The file is too big!");
        }

        avatarStudentService.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "{id}/avatar/preview")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        AvatarStudent avatarStudent = avatarStudentService.findAvatarStudent(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatarStudent.getMediaType()));
        headers.setContentLength(avatarStudent.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatarStudent.getData());
    }

    @GetMapping(value = "{id}/avatar")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        AvatarStudent avatarStudent = avatarStudentService.findAvatarStudent(id);

        Path path = Path.of(avatarStudent.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
             response.setStatus(200);
             response.setContentType(avatarStudent.getMediaType());
             response.setContentLength((int) avatarStudent.getFileSize());
             is.transferTo(os);
        }
    }

    @GetMapping("/count")
    public int getCountStudent() {
        return studentService.getCountStudents();
    }

    @GetMapping("/avgAge")
    public String getAvgAgeStudents() {
        return studentService.getAvgAgeStudents();
    }

    @GetMapping("/lastFive")
    public List<Student> getLastFive() {
        return studentService.getLastFive();
    }

    @GetMapping("/getAvatarsPage")
    public ResponseEntity<?> getAllAvatars(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(avatarStudentService.getAllAvatars(page, size));
    }

    @GetMapping("/get-start-a")
    public ResponseEntity<List<Student>> getStudentsStartA() {
        List<Student> student = studentService.findStudentStartA();
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }
}
