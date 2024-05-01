alter table users
drop constraint users_group_id_fk;

alter table users
    add constraint users_group_id_fk
        foreign key (group_id) references groups
            on update cascade on delete cascade;