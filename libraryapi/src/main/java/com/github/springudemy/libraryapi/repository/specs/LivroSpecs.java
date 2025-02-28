package com.github.springudemy.libraryapi.repository.specs;

import org.springframework.data.jpa.domain.Specification;

import com.github.springudemy.libraryapi.model.GeneroLivro;
import com.github.springudemy.libraryapi.model.Livro;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike(String titulo) {
        // like (%:param%)
        return (root, query, cb) -> cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero) {
        return (root, query, cb) -> cb.equal(root.get("genero"), genero);
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao){
        // and to_char(data_publicacao, 'YYYY') = :anoPublicacao
        return (root, query, cb) -> 
                cb.equal(cb.function("to_char", String.class,
                        root.get("dataPublicacao"), cb.literal("YYYY")), anoPublicacao.toString());
    }

    public static Specification<Livro> nomeAutorLike(String nome){
        return (root, query, cb) -> {

            Join<Object,Object> joinAutor = root.join("autor", JoinType.LEFT);

            return cb.like(cb.upper(joinAutor.get("nome")), "%" + nome.toUpperCase() + "%");

            // return cb.like(cb.upper(root.get("autor").get("nome")), "%" + nome.toUpperCase() + "%");
        };
    }

}
