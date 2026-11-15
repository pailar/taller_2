DROP DATABASE IF EXISTS taller_proyectos;
CREATE DATABASE taller_proyectos CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE taller_proyectos;

CREATE TABLE usuario (
    id        INT AUTO_INCREMENT PRIMARY KEY,
    nombre    VARCHAR(80)  NOT NULL,
    login     VARCHAR(40)  NOT NULL UNIQUE,
    pass      VARCHAR(60)  NOT NULL
);

INSERT INTO usuario (nombre, login, pass) VALUES
    ('Administrador', 'admin',  '1234'),
    ('Ana Perez',     'ana',    'ana123'),
    ('Matias',        'matias', 'mat123');

SELECT * FROM usuario;
