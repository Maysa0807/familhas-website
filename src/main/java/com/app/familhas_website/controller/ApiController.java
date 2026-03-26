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

    // POST /api/demo
    // Cria um novo item em memória.
    // Recebe texto no body (opcional), gera um id único e salva: id -> texto.
    // Retorna o id para usar nas próximas requisições.
    @PostMapping("/demo")
    public String post(@RequestBody(required = false) String corpo) {
        String id = UUID.randomUUID().toString(); // gera id aleatório único
        demo.put(id, corpo == null ? "" : corpo); // salva no mapa (se body vier null, salva vazio)
        return id; // devolve o id criado
    }
    // GET /api/demo/{id}
    // Busca no mapa o texto salvo para o id informado na URL.
    // Se não existir, retorna 404. Se existir, retorna 200 com o texto.
    @GetMapping("/demo/{id}")
    public ResponseEntity<String> get(@PathVariable String id) {
        String valor = demo.get(id); // tenta buscar no mapa
        if (valor == null) {
            return ResponseEntity.notFound().build(); // id não encontrado
        }
        return ResponseEntity.ok(valor); // id encontrado
    }
    // PUT /api/demo/{id}
    // Atualiza (ou cria) o valor completo para esse id.
    // No exemplo, substitui o conteúdo atual pelo body recebido.
    @PutMapping("/demo/{id}")
    public String put(@PathVariable String id, @RequestBody(required = false) String corpo) {
        demo.put(id, corpo == null ? "" : corpo); // grava/substitui no mapa
        return "PUT id=" + id; // mensagem de confirmação
    }
    // PATCH /api/demo/{id}
    // Em APIs reais, PATCH costuma atualizar parcialmente.
    // Neste exemplo simples, ele também só grava/substitui o texto no mapa.
    @PatchMapping("/demo/{id}")
    public String patch(@PathVariable String id, @RequestBody(required = false) String corpo) {
        demo.put(id, corpo == null ? "" : corpo); // grava atualização
        return "PATCH id=" + id; // mensagem de confirmação
    }
    // DELETE /api/demo/{id}
    // Remove o item daquele id do mapa em memória.
    @DeleteMapping("/demo/{id}")
    public String delete(@PathVariable String id) {
        demo.remove(id); // remove registro
        return "DELETE id=" + id; // mensagem de confirmação
    }
}

//MOCORONGAGEM DE Branch
//VOCE VAI SIM
