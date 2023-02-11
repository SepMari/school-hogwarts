package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.AvatarStudent;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarStudentRepository;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarStudentService {
    @Value("${path.to.avatar.folder}")
    private String avatarDir;

    private final StudentService studentService;
    private final AvatarStudentRepository avatarStudentRepository;

    Logger logger = LoggerFactory.getLogger(AvatarStudentService.class);

    public AvatarStudentService(StudentService studentService, AvatarStudentRepository avatarStudentRepository) {
        this.studentService = studentService;
        this.avatarStudentRepository = avatarStudentRepository;
    }

    public void uploadAvatar(Long id, MultipartFile file) throws IOException {
        Student student = studentService.findStudent(id);

        Path filePath = Path.of(avatarDir, student + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
             ) {
            bis.transferTo(bos);
        }

        AvatarStudent avatarStudent = findAvatarStudent(id);
        avatarStudent.setStudent(student);
        avatarStudent.setFilePath(filePath.toString());
        avatarStudent.setFileSize(file.getSize());
        avatarStudent.setMediaType(file.getContentType());
        avatarStudent.setData(file.getBytes());
        avatarStudentRepository.save(avatarStudent);

        logger.error("There is not student with id = {}", id);
    }

    public AvatarStudent findAvatarStudent(Long id) {
        logger.info("Was invoked method for find avatar student");
        return avatarStudentRepository.findByStudentId(id).orElse(new AvatarStudent());
    }

    private byte[] generateImagePreview(Path filePath) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


    public List<AvatarStudent> getAllAvatars(int page, int size) {
        logger.info("Was invoked method for get all avatars");
        return avatarStudentRepository.findAll(PageRequest.of(page - 1, size)).getContent();
    }
}
