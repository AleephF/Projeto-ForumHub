create table tickets(

    id bigint not null auto_increment,
    titulo varchar(100) not null unique,
    mensagem varchar(300) not null unique,
    dataDeCriacao TIMESTAMP not null default CURRENT_TIMESTAMP,
    statusTopico boolean not null,
    autor varchar(100) not null,
    curso varchar(100) not null,

    primary key(id)
);