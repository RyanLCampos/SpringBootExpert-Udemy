package com.github.springudemy.libraryapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.springudemy.libraryapi.service.TransacaoService;

@SpringBootTest
public class TransacoesTeste {

    @Autowired
    private TransacaoService transacaoService;

    /**
     * Commit -> Confirmar as alterações
     * Rollback -> Desfazer as alterações
     */
    @Test
    void transacaoSimples() {
        transacaoService.executar();
    }

    @Test
    void transacaoEstadoManaged(){
        transacaoService.atualizacaoSemAtualizar();
    }
}
