--liquibase formatted sql

--changeset yumeinaruu:9
--comment speciality to faculty delete and update on cascade
alter table speciality
drop constraint speciality_faculty_id_fk;

alter table speciality
    add constraint speciality_faculty_id_fk
        foreign key (faculty_id) references faculty
            on update cascade on delete cascade;