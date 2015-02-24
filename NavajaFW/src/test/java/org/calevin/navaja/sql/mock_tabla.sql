CREATE TABLE IF NOT EXISTS mock_tabla_prueba_dao (
atributo_varchar varchar(10) DEFAULT NULL,
atributo_int int(5) DEFAULT NULL,
segundo_atributo_int int(5) DEFAULT NULL,
tercer_atributo_int int(5) DEFAULT NULL,
cuarto_atributo_int int(5) DEFAULT NULL,
CONSTRAINT pk_mock PRIMARY KEY(atributo_varchar, atributo_int)
);