--liquibase formatted sql
--changeset wfeliciano20:003 splitStatements:true endDelimiter:;
ALTER TABLE directors
    ADD COLUMN birth_year INTEGER,
    ADD COLUMN country VARCHAR(255);

-- rollback ALTER TABLE directors DROP COLUMN birth_year, DROP COLUMN country;

ALTER TABLE actors
    ADD COLUMN birth_year INTEGER,
    ADD COLUMN country VARCHAR(255);

-- rollback ALTER TABLE actors DROP COLUMN birth_year, DROP COLUMN country;

