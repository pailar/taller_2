#SCRIPT PARA CREAR LA BASE DE DATOS MySQL DEL PROYECTO
#Se puede ejecutar completo en Workbench

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

alter table proyecto add FOREIGN KEY (id_funcionario_responsable) references funcionario(id);

insert into proyecto (nombre, descripcion, objetivo, fecha_inicio, fecha_termino, id_funcionario_responsable)
values ("Feria de Ciencias", "Feria anual de experimentos para 7° básico", "Presentar 10 experimentos a los apoderados", "2026-10-01", "2026-11-30", 1);
insert into proyecto (nombre, descripcion, objetivo, fecha_inicio, fecha_termino, id_funcionario_responsable)
values ("Museo de Historia Local", "Muestra con objetos y relatos del barrio", "Recopilar 30 piezas históricas", "2026-09-15", "2026-12-10", 2);
insert into proyecto (nombre, descripcion, objetivo, fecha_inicio, fecha_termino, id_funcionario_responsable)
values ("Radio Escolar", "Radio del colegio con programas de los alumnos", "Emitir un programa semanal", "2026-08-01", "2026-12-15", 5);

create table r_proyecto_funcionario(
id int auto_increment,
id_proyecto int,
id_funcionario int,
primary key (id),
unique (id_proyecto, id_funcionario),
foreign key (id_proyecto) references proyecto(id) on delete cascade,
foreign key (id_funcionario) references funcionario(id)
);

insert into r_proyecto_funcionario (id_proyecto, id_funcionario) values (1, 1);
insert into r_proyecto_funcionario (id_proyecto, id_funcionario) values (1, 3);
insert into r_proyecto_funcionario (id_proyecto, id_funcionario) values (1, 4);
insert into r_proyecto_funcionario (id_proyecto, id_funcionario) values (2, 2);
insert into r_proyecto_funcionario (id_proyecto, id_funcionario) values (2, 5);


create table linea(
id int auto_increment,
nombre varchar(100),
primary key (id)
);

insert into linea (nombre) values ("EXPERIMENTOS");
insert into linea (nombre) values ("DIFUSION");
insert into linea (nombre) values ("INFRAESTRUCTURA");
insert into linea (nombre) values ("CAPACITACION");

#proyecto puede tener 1 a N lineas y en descripcion se explica el porque (se detalla)
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

insert into r_proyecto_linea (id_proyecto, id_linea, descripcion)
values (1, 1, "Experimentos de química para 7° básico");

create table objetivo(
id int auto_increment,
nombre varchar(255),
alias varchar(30),
descripcion varchar(255),
orden int not null default 1,
evidencias varchar(255),
logro int not null default 0,
id_r_proyecto_linea int,
primary key (id)
);

alter table objetivo add FOREIGN KEY (id_r_proyecto_linea) references r_proyecto_linea(id) ON DELETE CASCADE;

insert into objetivo (nombre, alias, descripcion, orden, evidencias, logro, id_r_proyecto_linea)
values ("Preparar los materiales de la feria antes del 30 de noviembre.", "MATERIALES", "Compra de materiales", 1, "Lista de compras", 0, 1);
insert into objetivo (nombre, alias, descripcion, orden, evidencias, logro, id_r_proyecto_linea)
values ("Presentar 10 experimentos a los apoderados en la feria.", "EXPOSICION", "Presentación de experimentos", 2, "Fotos y pauta", 40, 1);


create table actividad(
id int auto_increment,
nombre varchar(100),
descripcion varchar(255),
id_objetivo int,
fecha_hora_inicio datetime,
fecha_hora_termino datetime,
pao boolean not null default false,
porcentaje int not null default 0,
observacion_control_gestion varchar(255),
id_funcionario_responsable int,
primary key (id)
);

alter table actividad add FOREIGN KEY (id_objetivo) references objetivo(id) ON DELETE CASCADE;
alter table actividad add FOREIGN KEY (id_funcionario_responsable) references funcionario(id);

insert into actividad (nombre, descripcion, id_objetivo, fecha_hora_inicio, fecha_hora_termino, pao, porcentaje, observacion_control_gestion, id_funcionario_responsable)
values ("Armar los stands en el gimnasio", "Montaje de mesas y carteles", 1, "2026-10-14 09:00:00", "2026-10-14 13:00:00", true, 0, "Sin observaciones", 3);


#los usuarios tienen un login y password para que se valide si se le da acceso al menu una vez logeado
create table usuario (
login varchar(10),
password varchar(10),
primary key (login)
);

insert into usuario values ('leo', '1234');
insert into usuario values ('matias', '1234');
insert into usuario values ('danae', '1234');