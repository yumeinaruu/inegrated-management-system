create table schedule
(
    id         bigserial                   not null
        constraint schedule_pk
            primary key,
    subject_id bigint                      not null
        constraint schedule_subject_id_fk
            references subject
            on update cascade on delete cascade,
    group_id   bigint                      not null
        constraint schedule_groups_id_fk
            references groups
            on update cascade on delete cascade,
    time       timestamp without time zone not null
);

