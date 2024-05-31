--liquibase formatted sql

--changeset yumeinaruu:16
--comment cascade deletion for marks added
alter table marks
drop constraint marks_users_id_fk;

alter table marks
    add constraint marks_users_id_fk
        foreign key (user_id) references users
            on update cascade on delete cascade;