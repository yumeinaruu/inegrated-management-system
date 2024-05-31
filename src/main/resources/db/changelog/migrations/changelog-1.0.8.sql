--liquibase formatted sql

--changeset yumeinaruu:8
--comment subject to department delete and update on cascade
alter table subject
drop constraint subject_department_id_fk;

alter table subject
    add constraint subject_department_id_fk
        foreign key (department_id) references department
            on update cascade on delete cascade;