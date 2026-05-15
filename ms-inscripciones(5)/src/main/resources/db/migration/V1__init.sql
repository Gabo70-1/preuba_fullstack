create table inscripciones (
    id integer not null AUTO_INCREMENT,
    fecha varchar(20),
    id_estudiante integer,
    id_curso integer,
    PRIMARY KEY (id)
);

INSERT INTO inscripciones (fecha, id_estudiante, id_curso)
VALUES ('2026-05-08', 1, 1);

INSERT INTO inscripciones (fecha, id_estudiante, id_curso)
VALUES ('2026-05-08', 2, 2);