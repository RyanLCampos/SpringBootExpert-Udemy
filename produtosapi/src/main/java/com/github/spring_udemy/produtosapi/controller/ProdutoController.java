package com.github.spring_udemy.produtosapi.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.spring_udemy.produtosapi.model.Produto;
import com.github.spring_udemy.produtosapi.repository.ProdutoRepository;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping("/cadastrar")
    public Produto salvar(@RequestBody Produto produto) {
        System.out.println("Produto recebido -> " + produto);

        // GERANDO ID, UUID -> GERA CODIGOS UNICOS.
        var id = UUID.randomUUID().toString();
        produto.setId(id);

        produtoRepository.save(produto);
        
        return produto;
    }

    @GetMapping("/{id}")
    public Produto obterProduto(@PathVariable String id){

        /* PRIMEIRA FORMA:
            Optional<Produto> produto = produtoRepository.findById(id);
            return produto.isPresent() ? produto.get() : null;
        */
        
        return produtoRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/remover/{id}")
    public String removerProduto(@PathVariable String id){

        if(produtoRepository.existsById(id)){
            produtoRepository.deleteById(id);
            return "Produto removido com sucesso!";
        }  

        return "Produto não encontrado!";
    }

    @PutMapping("/atualizar/{id}")
    public Produto atualizarProduto(@PathVariable String id, @RequestBody Produto produto){
        produto.setId(id);
        produtoRepository.save(produto);
        return produto;
    }

    @GetMapping()
    public List<Produto> buscar(@RequestParam("nome") String nome){
        return produtoRepository.findByNome(nome);
    }

}
