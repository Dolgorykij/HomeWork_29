package ru.hogwarts.school_HomeWork_29.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController {

    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/port")
    public String getPort() {
        return String.valueOf(serverPort);
    }
}
