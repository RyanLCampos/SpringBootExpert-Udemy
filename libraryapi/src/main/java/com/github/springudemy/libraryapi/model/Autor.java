package com.github.springudemy.libraryapi.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "autor", schema = "public") // Não necessario colocar public (Default)
@Data
@ToString(exclude = "livros")
@EntityListeners(AuditingEntityListener.class) // Ficara vendo se ocorrer alguma alteração
public class Autor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nacionalidade;

    // Poderia utilizar cascade, caso deletar autor, removeria os livros.
    // FetchType.LAZY - Irá puxar os livros somente quando requisitado.
    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY) // Um autor para muitos livros
    private List<Livro> livros;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
