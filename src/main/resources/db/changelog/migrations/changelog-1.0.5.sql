--liquibase formatted sql

--changeset yumeinaruu:5
--comment department name set varchar
alter table department
alter column name type varchar using name::varchar;
