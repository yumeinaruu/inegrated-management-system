--liquibase formatted sql

--changeset yumeinaruu:4
--comment add security id
alter table security
    add id bigserial not null;

alter table security
    add constraint security_pk
        primary key (id);