USE db_teste;

CREATE TABLE tb_user (
    id integer not null auto_increment,
    name varchar(200),
    cpf varchar(11)
);

SET character_set_client = utf8;
SET character_set_connection = utf8;
SET character_set_results = utf8;
SET collation_connection = utf8_general_ci;

INSERT INTO user (name, cpf) VALUES ('Robyson Gasparin', '00973162961');