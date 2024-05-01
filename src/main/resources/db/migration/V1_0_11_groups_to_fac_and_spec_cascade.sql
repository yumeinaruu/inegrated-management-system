alter table groups
drop constraint group_faculty_id_fk;

alter table groups
    add constraint group_faculty_id_fk
        foreign key (faculty_id) references faculty
            on update cascade on delete cascade;

alter table groups
drop constraint group_speciality_id_fk;

alter table groups
    add constraint group_speciality_id_fk
        foreign key (speciality_id) references speciality
            on update cascade on delete cascade;
