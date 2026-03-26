package com.app.familhas_website.controller;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ConcurrentHashMap<String, String> demo = new ConcurrentHashMap<>();

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }

    @GetMapping("/hi")
    public String sayHi() {
        return "Hi, how are you?";
    }

    @PostMapping("/demo")
    public String post(@RequestBody(required = false) String corpo) {
        String id = UUID.randomUUID().toString();
        demo.put(id, corpo == null ? "" : corpo);
        return id;
    }

    @GetMapping("/demo/{id}")
    public ResponseEntity<String> get(@PathVariable String id) {
        String valor = demo.get(id);
        if (valor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(valor);
    }

    @PutMapping("/demo/{id}")
    public String put(@PathVariable String id, @RequestBody(required = false) String corpo) {
        demo.put(id, corpo == null ? "" : corpo);
        return "PUT id=" + id;
    }

    @PatchMapping("/demo/{id}")
    public String patch(@PathVariable String id, @RequestBody(required = false) String corpo) {
        demo.put(id, corpo == null ? "" : corpo);
        return "PATCH id=" + id;
    }

    @DeleteMapping("/demo/{id}")
    public String delete(@PathVariable String id) {
        demo.remove(id);
        return "DELETE id=" + id;
    }
}
