# Sistema de Gestión de Adopciones

Este proyecto implementa una API REST para gestionar el proceso de adopción de mascotas en un refugio. 
El sistema permite registrar especies, gestionar mascotas y realizar adopciones, asegurando la integridad de los datos y evitando la doble adopción.

## Problemática
El refugio necesita digitalizar el control de animales. Se requiere categorizar especies, registrar mascotas y rastrear las adopciones realizadas para evitar que mascotas ya adoptadas sigan apareciendo como disponibles.

## Requerimientos Técnicos
- **Lenguaje:** Java (Servlets, JDBC)
- **Base de Datos:** MySQL
- **Formato de Intercambio:** JSON
- **Herramienta de Construcción:** Maven

## Estructura de la Base de Datos
El sistema cuenta con tres tablas principales:
1. **especies**: Categorías de animales (perro, gato, etc.).
2. **mascotas**: Información de los animales disponibles para adopción.
3. **adopciones**: Registro de las adopciones realizadas.

## Configuración y Ejecución
1. Ejecutar el script `script.sql` en su servidor MySQL local.
2. Configurar las credenciales de base de datos en `src/main/java/com/refugio/bd/ConexionBD.java` si son diferentes a `root` / `` (vacío).
3. Compilar y ejecutar con Maven: `mvn clean package` y desplegar el WAR en un servidor Tomcat, o usar `mvn numbertomcat7:run` (si se agrega el plugin).
