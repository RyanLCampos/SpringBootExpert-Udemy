package com.github.spring_udemy.arquiteturaspring.todos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoEntity, Integer> {

    boolean existsByDescricao(String descricao);

}
