package com.github.spring_udemy.arquiteturaspring.todos;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/criar")
    public TodoEntity salvar(@RequestBody TodoEntity todoEntity) {
        try {
            return todoService.salvar(todoEntity);
        } catch (IllegalArgumentException e) {
            var mensagem  = e.getMessage();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, mensagem);
        }
    }

    @PutMapping("/atualizar/{id}")
    public void atualizarStatus(@PathVariable("id") Integer id, @RequestBody TodoEntity todo) {
        todo.setId(id);
        todoService.atualizarStatus(todo);
    }

    @GetMapping("/buscar/{id}")
    public TodoEntity buscar(@PathVariable("id") Integer id) {
        return todoService.buscarPorId(id);
    }

}
