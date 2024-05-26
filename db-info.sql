create sequence group_id_seq;

alter sequence group_id_seq owner to postgres;

create table faculty
(
    id   bigserial
        constraint faculty_pk
            primary key,
    name varchar not null
);

alter table faculty
    owner to postgres;

create unique index faculty_name_uindex
    on faculty (name);

create table department
(
    id   bigserial
        constraint department_pk
            primary key,
    name varchar not null
);

alter table department
    owner to postgres;

create unique index department_name_uindex
    on department (name);

create table speciality
(
    id         bigserial
        constraint speciality_pk
            primary key,
    name       varchar not null,
    faculty_id bigint  not null
        constraint speciality_faculty_id_fk
            references faculty
            on update cascade on delete cascade
);

alter table speciality
    owner to postgres;

create unique index speciality_name_uindex
    on speciality (name);

create table groups
(
    name          varchar                                          not null,
    id            bigint default nextval('group_id_seq'::regclass) not null
        constraint group_pk
            primary key,
    faculty_id    bigint                                           not null
        constraint group_faculty_id_fk
            references faculty
            on update cascade on delete cascade,
    speciality_id bigint                                           not null
        constraint group_speciality_id_fk
            references speciality
            on update cascade on delete cascade
);

alter table groups
    owner to postgres;

alter sequence group_id_seq owned by groups.id;

create unique index group_name_uindex
    on groups (name);

create table users
(
    id       bigserial
        constraint users_pk
            primary key,
    username varchar   not null,
    created  timestamp not null,
    changed  timestamp,
    group_id bigint
        constraint users_group_id_fk
            references groups
            on update cascade
);

alter table users
    owner to postgres;

create table security
(
    login    varchar not null,
    password varchar not null,
    role     varchar not null,
    user_id  bigint  not null
        constraint security_users_id_fk
            references users
            on update cascade on delete cascade,
    id       bigserial
        constraint security_pk
            primary key
);

alter table security
    owner to postgres;

create unique index security_login_uindex
    on security (login);

create table subject
(
    id            bigserial
        constraint subject_pk
            primary key,
    name          varchar not null,
    department_id bigint  not null
        constraint subject_department_id_fk
            references department
            on update cascade on delete cascade
);

alter table subject
    owner to postgres;

create unique index subject_name_uindex
    on subject (name);

create table marks
(
    id         bigserial
        constraint marks_pk
            primary key,
    mark       integer,
    subject_id bigint not null
        constraint marks_subject_id_fk
            references subject
            on update cascade on delete cascade,
    user_id    bigint not null
        constraint marks_users_id_fk
            references users
            on update cascade on delete cascade
);

alter table marks
    owner to postgres;

create table flyway_schema_history
(
    installed_rank integer                 not null
        constraint flyway_schema_history_pk
            primary key,
    version        varchar(50),
    description    varchar(200)            not null,
    type           varchar(20)             not null,
    script         varchar(1000)           not null,
    checksum       integer,
    installed_by   varchar(100)            not null,
    installed_on   timestamp default now() not null,
    execution_time integer                 not null,
    success        boolean                 not null
);

alter table flyway_schema_history
    owner to postgres;

create index flyway_schema_history_s_idx
    on flyway_schema_history (success);

create table schedule
(
    id         bigserial
        constraint schedule_pk
            primary key,
    subject_id bigint    not null
        constraint schedule_subject_id_fk
            references subject
            on update cascade on delete cascade,
    group_id   bigint    not null
        constraint schedule_groups_id_fk
            references groups
            on update cascade on delete cascade,
    beginning  timestamp not null,
    ending     timestamp not null
);

alter table schedule
    owner to postgres;

