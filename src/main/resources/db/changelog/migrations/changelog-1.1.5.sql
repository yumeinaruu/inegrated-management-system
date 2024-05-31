--liquibase formatted sql

--changeset yumeinaruu:14
--comment cascade deletion for groups removed
alter table public.users
drop constraint users_group_id_fk;

alter table public.users
    add constraint users_group_id_fk
        foreign key (group_id) references public.groups
            on update cascade;