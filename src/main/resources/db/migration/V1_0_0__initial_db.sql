create table public.faculty
(
    id   bigserial
        constraint faculty_pk
            primary key,
    name varchar not null
);

alter table public.faculty
    owner to postgres;

create unique index faculty_name_uindex
    on public.faculty (name);

create table public.department
(
    id   bigserial
        constraint department_pk
            primary key,
    name integer not null
);

alter table public.department
    owner to postgres;

create unique index department_name_uindex
    on public.department (name);

create table public.speciality
(
    id         bigserial
        constraint speciality_pk
            primary key,
    name       varchar not null,
    faculty_id bigint  not null
        constraint speciality_faculty_id_fk
            references public.faculty
);

alter table public.speciality
    owner to postgres;

create table public."group"
(
    group_name    varchar not null,
    id            bigserial
        constraint group_pk
            primary key,
    faculty_id    bigint  not null
        constraint group_faculty_id_fk
            references public.faculty,
    speciality_id bigint  not null
        constraint group_speciality_id_fk
            references public.speciality
);

alter table public.groups
    owner to postgres;

create table public.users
(
    id       bigserial
        constraint users_pk
            primary key,
    username varchar   not null,
    created  timestamp not null,
    changed  timestamp,
    group_id bigint
        constraint users_group_id_fk
            references public.groups
);

alter table public.users
    owner to postgres;

create unique index users_username_uindex
    on public.users (username);

create table public.security
(
    id       integer not null
        constraint security_pk
            primary key,
    login    varchar not null,
    password varchar not null,
    role     varchar not null,
    user_id  bigint  not null
        constraint security_users_id_fk
            references public.users
            on update cascade on delete cascade
);

alter table public.security
    owner to postgres;

create unique index security_login_uindex
    on public.security (login);

create unique index speciality_name_uindex
    on public.speciality (name);

create table public.subject
(
    id            bigserial
        constraint subject_pk
            primary key,
    name          varchar not null,
    department_id bigint  not null
        constraint subject_department_id_fk
            references public.department
);

alter table public.subject
    owner to postgres;

create unique index subject_name_uindex
    on public.subject (name);

create table public.marks
(
    id         bigserial
        constraint marks_pk
            primary key,
    mark       integer,
    subject_id bigint not null
        constraint marks_subject_id_fk
            references public.subject,
    user_id    bigint not null
        constraint marks_users_id_fk
            references public.users
);

alter table public.marks
    owner to postgres;

create table public.flyway_schema_history
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

alter table public.flyway_schema_history
    owner to postgres;

create index flyway_schema_history_s_idx
    on public.flyway_schema_history (success);

