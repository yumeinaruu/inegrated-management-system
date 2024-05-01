alter table subject
drop constraint subject_department_id_fk;

alter table subject
    add constraint subject_department_id_fk
        foreign key (department_id) references department
            on update cascade on delete cascade;