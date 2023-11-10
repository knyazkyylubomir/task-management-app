package org.project.name.task.management.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public String sayHi(Authentication authentication) {
        return "Hello, user " + authentication.getName();
    }
}
