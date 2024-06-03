--liquibase formatted sql

--changeset yumeinaruu:17
--comment deleted not null constraint for user_id to create superadmin faster
alter table public.security
    alter column user_id drop not null;