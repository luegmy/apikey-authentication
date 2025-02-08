# API de Gestión de Usuarios

## Descripción

### Este proyecto es una demo API desarrollada en Spring Boot 3.2.x que permite la creación y autenticación de usuarios. Se ha implementado utilizando spring-data, el patrón mapper, y la autenticación mediante API-Key.

## Tecnologías Utilizadas

- Spring Boot 3.2.x
- Spring Data JPA
- MapStruct para el mapeo de entidades y DTOs
- MySQL como base de datos
- JUnit y Mockito para pruebas unitarias

## Endpoints del API

- POST /api/users/create → Crea un usuario (requiere API Key y rol ADMIN).

- GET /api/users → Lista los usuarios (requiere autenticación).

## Seguridad

### El proyecto utiliza autenticación basada en API Key y control de acceso con Spring Security..

## Scripts de Creación de Base de Datos

```
CREATE DATABASE bd_demo;
USE bd_demo;

CREATE TABLE rol (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE permission (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    account_No_Expired BOOLEAN NOT NULL,
    account_No_Locked BOOLEAN NOT NULL,
    credentials_No_Expired BOOLEAN NOT NULL
);

CREATE TABLE rol_user (
    user_id INT NOT NULL,
    rol_id INT NOT NULL,
    PRIMARY KEY (user_id, rol_id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (rol_id) REFERENCES rol(id)
);

CREATE TABLE rol_permission (
    rol_id INT NOT NULL,
    permission_id INT NOT NULL,
    PRIMARY KEY (rol_id, permission_id),
    FOREIGN KEY (rol_id) REFERENCES rol(id),
    FOREIGN KEY (permission_id) REFERENCES permission(id)
);
```
## Inserciones Iniciales
```
INSERT INTO rol (name) VALUES ('ADMIN'), ('USER'), ('INVITED'), ('DEVELOPER');

INSERT INTO permission (name) VALUES ('CREATE_USER'), ('DELETE_USER'), ('VIEW_USER'), ('EDIT_USER'), ('MANAGE_PERMISSIONS');

INSERT INTO user (username, password, enabled, account_No_Expired, account_No_Locked, credentials_No_Expired) VALUES 
('admin', 'hashed_password', 1, 1, 1, 1),
('user1', 'hashed_password', 1, 1, 1, 1),
('moderator', 'hashed_password', 1, 1, 1, 1),
('editor', 'hashed_password', 1, 1, 1, 1),
('guest', 'hashed_password', 1, 1, 1, 1);

INSERT INTO rol_user (user_id, rol_id) VALUES 
(1, 1), (2, 2), (3, 3), (4, 4), (5, 4);

INSERT INTO rol_permission (rol_id, permission_id) VALUES 
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5),
(2, 3),
(3, 3), 
(4, 3), (4, 4);
```
## Seleccionar usuario, rol y permiso
```
SELECT 
    u.username,
    r.name AS rol_name,
    p.name AS permission_name
FROM
    user u
        INNER JOIN
    rol_user ur ON u.id = ur.user_id
        INNER JOIN
    rol r ON ur.rol_id = r.id
        INNER JOIN
    rol_permission rp ON r.id = rp.rol_id
        INNER JOIN
    permission p ON rp.permission_id = p.id;
```