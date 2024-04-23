alter table security
    add id bigserial not null;

alter table security
    add constraint security_pk
        primary key (id);