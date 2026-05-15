SISTEMA DE GESTION ACADEMICA

INTEGRANTES
-Nicolas Chauca
-Gabriel Yañez

DESCRIPCION
Sistema desarrollado con arquitectura de microservicios usando spring boot para la getion academica de una institucion.

EL SISTEMA PERMITE ADMINISTRAR
-Estudiantes
-Profesores
-Facultades
-Cursos
-Inscripciones
-Evaluaciones
-Notas
-Asistencia
-Horarios
-Historial academico

Tambien incorpora la autentificacion y autorizacion mediante JWT. 

MICROSERVICIOS Y FUNCION
1. ms-auth                 / seguridad y autenticacion
2. ms-facultad             / gestion de facultades
3. ms-estudiantes          / gestion de estudiantes
4. ms-profesores           / gestion de profesores
5. ms-cursos               / gestion de cursos
6. ms-inscripciones        / inscripcion de estudiantes
7. ms-evaluaciones         / gestion de evaluaciones
8. ms-notas                / gestion de notas
9. ms-asistencia           / control de asistencia
10. ms-horario             / gestion de horario
11. ms-historial-academico / resumen academico


PASOS PARA EJECUTAR

1. Clonar el repositorio(visual studio code)
   git clone https://github.com/Gabo70-1/preuba_fullstack.git

2. Abrir laragon
   iniciar servicios(opcion iniciar todo)
   -apache
   -mysql
   entrar a la base de datos

3. Crear las bases de datos para los microservicios

CREATE DATABASE ms_user;
CREATE DATABASE ms_asistencia;
CREATE DATABASE ms_cursos;
CREATE DATABASE ms_estudiantes;
CREATE DATABASE ms_evaluaciones;
CREATE DATABASE ms_facultad;
CREATE DATABASE ms_historial_academico;
CREATE DATABASE ms_horario;
CREATE DATABASE ms_inscripciones;
CREATE DATABASE ms_notas;
CREATE DATABASE ms_profesor;

4. Abrir cada microservicio en visual studio code y ejecutarlos

5. abrir postman
Registrar usuario utilizando el endpoint:
  POST http://localhost:8092/auth/register

Luego utilizar el token JWT en los demás microservicios desde Headers:

  Authorization: Bearer token_generado

Con eso listo, se pueden utilizar los endpoints de los demás microservicios.       

TECNOLOGIAS UTILIZADAS
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Flyway
- Spring Security
- JWT
- WebClient
- Maven
- Lombok
- Visual Studio Code
- Postman
- Laragon
