alter table marks
drop constraint marks_subject_id_fk;

alter table marks
    add constraint marks_subject_id_fk
        foreign key (subject_id) references subject
            on update cascade on delete cascade;