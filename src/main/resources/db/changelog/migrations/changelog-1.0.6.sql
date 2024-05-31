--liquibase formatted sql

--changeset yumeinaruu:6
--comment group_name renamed to just name
alter table groups
    rename column group_name to name;

alter index group_group_name_uindex rename to group_name_uindex;