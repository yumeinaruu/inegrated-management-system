--liquibase formatted sql

--changeset yumeinaruu:3
--comment drop security pk
alter table security
drop constraint security_pk;

alter table security
drop column id;