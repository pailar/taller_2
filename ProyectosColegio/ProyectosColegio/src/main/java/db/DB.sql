#SCRIPT DE EJEJMPLO PARA CREAR UNA BASE DE DATOS MySQL
#Se puede utilizar workbench para ejecutar este script y/o modificarlo

#EQUIVALENCIAS DE TIPOS DE DATOS EN CASO DE QUE REALICEN UN PROGRAMA EN JAVA CON MySQL
#SQL               Java
#############-
#BIT               boolean
#TINYINT           byte
#SMALLINT          short
#INTEGER           int
#BIGINT            long
#FLOAT             double
#REAL              float
#DOUBLE            double
#CHAR              String
#VARCHAR           String
#DATE              Date


#DE AQUI EN ADELANTE INSTRUCCIONES DDL PARA CREAR Y DEFINIR LA ESTRUCTURA DE TABLAS DE LA BASE DE DATOS
#CON SUS CORRESPONDIENTES CLAVES PRIMARIAS(PK) Y CLAVES FORANEAS(FK)


#en caso que la base de datos ya exista y se requiere eliminar para crearla desde cero, activar del drop database
#pueden usar el nombre de base de datos que quieran, en este ejemplo se llama 'loginapp'
drop database if exists proyectos;
CREATE DATABASE  IF NOT EXISTS  proyectos;

#es necesario el use para que se active la base de datos de tal manera que las tablas se creen dentro de esta base
use proyectos;

create table funcionario(
id int,
rut varchar(12),
nombre varchar(20),
primary key (id)
);

insert into funcionario values(1,"11.111.111-1", "funcionario1");

create table proyecto(
id int,
nombre varchar(20),
id_funcionario_responsable int,
primary key (id)
);

insert into proyecto values(1,"proyecto1",1);
insert into proyecto values(2,"proyecto2",1);
insert into proyecto values(3proyectoproyecto,"proyecto3",1);

alter table proyecto add FOREIGN KEY (id_funcionario_responsable) references funcionario(id);

create table linea(
id int,
nombre varchar(20),
primary key (id)
);

#proyecto puede tener 1 a N lineas y en descripcion se explica el porque (se detalla)
create table r_proyecto_linea(
id int,
id_proyecto int,
id_linea int,
descripcion varchar(255),
primary key (id)
);

ALTER TABLE r_proyecto_linea
ADD CONSTRAINT uq_proyecto_linea UNIQUE (id_proyecto, id_linea);

alter table r_proyecto_linea add FOREIGN KEY (id_proyecto) references proyecto(id);
alter table r_proyecto_linea add FOREIGN KEY (id_linea) references linea(id);

create table objetivo(
id int,
nombre varchar(20),
id_r_proyecto_linea int,
primary key (id)
);

alter table objetivo add FOREIGN KEY (id_r_proyecto_linea) references r_proyecto_linea(id);

create table actividad(
id int,
nombre varchar(20),
id_objetivo int,
primary key (id)
);

alter table actividad add FOREIGN KEY (id_objetivo) references objetivo(id);



#los usuarios tienen un login y password para que se valide si se le da acceso al menu una vez logeado
create table usuario (
login varchar(10),
password varchar(10),
primary key (login)
);




insert into  usuario values ('unsuario', 'mipassword');