package com.github.spring_udemy.arquiteturaspring.todos;

import org.springframework.stereotype.Service;

@Service
public class TodoService {

    /*
     * @Autowired
     * Utilizando o construtor para fazer a injeção de dependencias
     * no lugar do @Autowired
     */

    private TodoRepository todoRepository;
    private TodoValidator todoValidator;
    private MailSender mailSender;

    public TodoService(TodoRepository todoRepository,
                       TodoValidator todoValidator,
                       MailSender mailSender) {

        this.todoRepository = todoRepository;
        this.todoValidator = todoValidator;
        this.mailSender = mailSender;
    }

    public TodoEntity salvar(TodoEntity novoTodo) {
        todoValidator.validar(novoTodo);
        return todoRepository.save(novoTodo);
    }

    public void atualizarStatus(TodoEntity todo) {
        todoRepository.save(todo);

        String status = todo.getConcluido() == Boolean.TRUE ? "Concluido" : "Não concluido";

        mailSender.enviar("TODO -> '" + todo.getDescricao() + "', foi atualizado para " + status);
    }

    public TodoEntity buscarPorId(Integer id) {
        return todoRepository.findById(id).orElse(null);
    }

}
