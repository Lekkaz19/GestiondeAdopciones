-- Creación de la base de datos
DROP DATABASE IF EXISTS adopciones_db;
CREATE DATABASE adopciones_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE adopciones_db;

-- Tabla: especies
CREATE TABLE especies (
    id_especie INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

-- Tabla: mascotas
CREATE TABLE mascotas (
    id_mascota INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    edad INT NOT NULL,
    raza VARCHAR(50),      
    descripcion TEXT,      
    id_especie INT NOT NULL,
    adoptado BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_especie) REFERENCES especies(id_especie)
);

-- Tabla: adopciones
CREATE TABLE adopciones (
    id_adopcion INT AUTO_INCREMENT PRIMARY KEY,
    nombre_adoptante VARCHAR(150) NOT NULL,
    telefono VARCHAR(20) NOT NULL, 
    email VARCHAR(100),            
    fecha_adopcion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_mascota INT NOT NULL,
    UNIQUE(id_mascota), -- Una mascota solo puede ser adoptada una vez
    FOREIGN KEY (id_mascota) REFERENCES mascotas(id_mascota)
);

-- Datos de prueba
INSERT INTO especies (nombre) VALUES ('Perro'), ('Gato'), ('Conejo');

INSERT INTO mascotas (nombre, edad, raza, descripcion, id_especie, adoptado) VALUES 
('Max', 3, 'Labrador', 'Juguetón y le gustan los niños', 1, FALSE),
('Luna', 2, 'Siames', 'Tranquila, ideal para departamento', 2, FALSE),
('Rocky', 5, 'Pastor Aleman', 'Guardian y leal', 1, TRUE), -- Ya adoptado
('Tambor', 1, 'Cabeza de Leon', 'Peludo y amigable', 3, FALSE);

INSERT INTO adopciones (nombre_adoptante, telefono, email, id_mascota) VALUES 
('Carlos Pérez', '555-1234', 'carlos@email.com', 3);
