CREATE DATABASE IF NOT EXISTS proyectos;
USE proyectos;

CREATE TABLE proyecto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    fecha_inicio DATE,
    fecha_termino DATE
);

CREATE TABLE objetivo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    proyecto_id INT NOT NULL,
    descripcion TEXT NOT NULL,
    FOREIGN KEY (proyecto_id) REFERENCES proyecto(id) ON DELETE CASCADE
);

CREATE TABLE linea (
    id INT AUTO_INCREMENT PRIMARY KEY,
    proyecto_id INT NOT NULL,
    nombre_linea VARCHAR(100) NOT NULL,
    FOREIGN KEY (proyecto_id) REFERENCES proyecto(id) ON DELETE CASCADE,
);

CREATE TABLE actividad (
    id INT AUTO_INCREMENT PRIMARY KEY,
    proyecto_id INT NOT NULL,
    linea_id INT,
    nombre VARCHAR(100) NOT NULL,
    fecha DATE,
    estado VARCHAR(50),
    FOREIGN KEY (proyecto_id) REFERENCES proyecto(id) ON DELETE CASCADE,
    FOREIGN KEY (linea_id) REFERENCES linea(id) ON DELETE SET NULL
);

