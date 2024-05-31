--liquibase formatted sql

--changeset yumeinaruu:2
--comment add index to group name
create unique index group_group_name_uindex
    on groups (group_name);