package com.github.spring_udemy.arquiteturaspring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.spring_udemy.arquiteturaspring.todos.TodoEntity;
import com.github.spring_udemy.arquiteturaspring.todos.TodoValidator;

@Lazy // Instancia somente quando for utilizado 
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON) // Outra forma de utilizar o SCOPE
// @Scope(WebApplicationContext.SCOPE_APPLICATION) - Outra Forma
// @Scope("request") Não Guarda Estado
// @Scope("session") Unico usuario - Guarda Estado
// @Scope("application") Todos os usuarios - Guarda Estado
public class BeanGerenciado {

    // Seguna melhor forma:
    // @Autowired
    private TodoValidator todoValidator;

    // @Autowired
    // public void setValidator(TodoValidator todoValidator){
    //     this.todoValidator = todoValidator;
    // }

    // Forma indicada de injeção de dependências
    public BeanGerenciado(TodoValidator todoValidator){
        this.todoValidator = todoValidator;
    }

    public void validar(TodoEntity todo){
        todoValidator.validar(todo);
    }
}
