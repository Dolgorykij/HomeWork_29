package ru.hogwarts.school_HomeWork_29.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school_HomeWork_29.Repository.AvatarRepository;
import ru.hogwarts.school_HomeWork_29.model.Avatar;
import ru.hogwarts.school_HomeWork_29.model.Student;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.swagger.v3.core.util.AnnotationsUtils.getExtensions;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    @Value("${students.avatar.dir.path}")
    private String avatarsDir;

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }


    public void uploadAvatar (Long studentId, MultipartFile file) throws IOException {
        Student student = studentService.findStudent(studentId);
        Path filePath = Path.of(avatarsDir, studentId + "." + getExtensions(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize((int)file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        avatarRepository.save(avatar);
    }
    public Avatar findAvatar (Long studentId) {
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }


    private String getExtensions (String fileName) {
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }
}
