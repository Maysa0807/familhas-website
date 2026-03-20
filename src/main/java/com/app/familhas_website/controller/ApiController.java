package com.app.familhas_website.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping ("/hello")
    public String sayHello() {
        return "Hello, World!";
    }

    @GetMapping ("/hi")
    public String sayHi() {
        return "Hi, how are you?";
    }
}

