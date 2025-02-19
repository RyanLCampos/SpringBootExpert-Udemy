create table produto(
    codigo varchar(255) not null primary key,
    nome varchar(255) not null,
    descricao varchar(300),
    preco numeric(18, 2)
);