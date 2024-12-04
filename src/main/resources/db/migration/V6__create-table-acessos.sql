create table usuarios(

    id bigint not null auto_increment,
    login varchar(150) not null,
    senha varchar(300) not null,

    primary key(id)
);