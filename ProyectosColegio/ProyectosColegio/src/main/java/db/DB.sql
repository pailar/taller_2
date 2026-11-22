drop database if exists proyectos;
CREATE DATABASE IF NOT EXISTS proyectos CHARACTER SET utf8mb4;

use proyectos;

create table funcionario(
id int auto_increment,
rut varchar(12),
nombre varchar(50),
primary key (id)
);

insert into funcionario (rut, nombre) values ("11.111.111-1", "Ana Pérez");
insert into funcionario (rut, nombre) values ("12.222.222-2", "Luis Mora");
insert into funcionario (rut, nombre) values ("13.333.333-3", "Camila Rojas");
insert into funcionario (rut, nombre) values ("14.444.444-4", "Diego Fuentes");
insert into funcionario (rut, nombre) values ("15.555.555-5", "Valentina Soto");

create table proyecto(
id int auto_increment,
nombre varchar(100),
descripcion varchar(500),
objetivo varchar(255),
fecha_inicio date,
fecha_termino date,
id_funcionario_responsable int,
primary key (id)
);

insert into proyecto (nombre, descripcion, objetivo, fecha_inicio, fecha_termino, id_funcionario_responsable)
values ("Feria de Ciencias", "Feria anual de experimentos para 7° básico", "Presentar 10 experimentos a los apoderados", "2026-10-01", "2026-11-30", 1);
insert into proyecto (nombre, descripcion, objetivo, fecha_inicio, fecha_termino, id_funcionario_responsable)
values ("Museo de Historia Local", "Muestra con objetos y relatos del barrio", "Recopilar 30 piezas históricas", "2026-09-15", "2026-12-10", 2);
insert into proyecto (nombre, descripcion, objetivo, fecha_inicio, fecha_termino, id_funcionario_responsable)
values ("Radio Escolar", "Radio del colegio con programas de los alumnos", "Emitir un programa semanal", "2026-08-01", "2026-12-15", 5);

alter table proyecto add FOREIGN KEY (id_funcionario_responsable) references funcionario(id);

create table linea(
id int auto_increment,
nombre varchar(100),
primary key (id)
);

create table r_proyecto_linea(
id int auto_increment,
id_proyecto int,
id_linea int,
descripcion varchar(255),
primary key (id)
);

ALTER TABLE r_proyecto_linea
ADD CONSTRAINT uq_proyecto_linea UNIQUE (id_proyecto, id_linea);

alter table r_proyecto_linea add FOREIGN KEY (id_proyecto) references proyecto(id) ON DELETE CASCADE;
alter table r_proyecto_linea add FOREIGN KEY (id_linea) references linea(id);

create table objetivo(
id int auto_increment,
nombre varchar(255),
id_r_proyecto_linea int,
primary key (id)
);

alter table objetivo add FOREIGN KEY (id_r_proyecto_linea) references r_proyecto_linea(id) ON DELETE CASCADE;

create table actividad(
id int auto_increment,
nombre varchar(100),
id_objetivo int,
primary key (id)
);

alter table actividad add FOREIGN KEY (id_objetivo) references objetivo(id) ON DELETE CASCADE;

create table usuario (
login varchar(10),
password varchar(10),
primary key (login)
);

insert into usuario values ('unsuario', 'mipassword');