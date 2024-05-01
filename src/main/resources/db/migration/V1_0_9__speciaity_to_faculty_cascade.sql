alter table speciality
drop constraint speciality_faculty_id_fk;

alter table speciality
    add constraint speciality_faculty_id_fk
        foreign key (faculty_id) references faculty
            on update cascade on delete cascade;