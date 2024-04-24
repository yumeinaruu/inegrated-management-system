alter table "group"
    rename column group_name to name;

alter index group_group_name_uindex rename to group_name_uindex;