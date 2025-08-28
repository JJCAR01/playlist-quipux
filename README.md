# API REST de Playlists

API REST desarrollada con Spring Boot, JPA y H2 para gestionar playlists de música.

## Características

- ✅ REST API completa con endpoints CRUD
- ✅ Autenticación y autorización con Spring Security
- ✅ Persistencia en base de datos H2 en memoria
- ✅ Pruebas unitarias completas
- ✅ Validación de datos
- ✅ Manejo de errores HTTP apropiado con respuestas JSON detalladas
- ✅ Excepciones personalizadas para mejor control de errores

## Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.5.0**
- **Spring Data JPA**
- **Spring Security**
- **H2 Database**
- **Maven**
- **JUnit 5**
- **Lombok**

## Arquitectura

Arquitectura Hexagonal

### Autenticación

La API utiliza autenticación HTTP Basic. Usuarios disponibles:

- **admin/admin123** (roles: USER, ADMIN)

### Endpoints

| Método    | Endpoint              | Descripción                   | Códigos de Respuesta |
|--------   |----------             |-------------                  |---------------------|
| POST      | `/lists`              | Crear nueva playlist          | 201 Created, 400 Bad Request |
| GET       | `/lists`              | Obtener todas las playlists   | 200 OK |
| GET       | `/lists/{listName}`   | Obtener playlist por nombre   | 200 OK, 404 Not Found |
| DELETE    | `/lists/{listName}`   | Eliminar playlist             | 204 No Content, 404 Not Found |

