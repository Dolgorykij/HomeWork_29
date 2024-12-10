package ru.hogwarts.school_HomeWork_29.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school_HomeWork_29.Repository.AvatarRepository;
import ru.hogwarts.school_HomeWork_29.model.Avatar;
import ru.hogwarts.school_HomeWork_29.model.Student;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static io.swagger.v3.core.util.AnnotationsUtils.getExtensions;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    private static final Logger logger = LoggerFactory.getLogger(AvatarService.class);

    //@Value("${students.avatar.dir.path}")
    private String avatarsDir = "avatars";

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

        logger.info("Avatar успешно сохранен: {}", avatar);
        avatarRepository.save(avatar);
    }
    public Avatar findAvatar (Long studentId) {
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElse(new Avatar());
        if (avatar.getId() != null) {
            logger.info("Avatar найден: {}", avatar);
        } else {
            logger.error("У студента с таким ID: {} аватар не найден", studentId);
        }
        return avatar;
    }

//
    private String getExtensions (String fileName) {
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }


    public List<Avatar> findAll(Integer pageNumber, Integer pageSize) {
        return avatarRepository.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent();
    }
}
