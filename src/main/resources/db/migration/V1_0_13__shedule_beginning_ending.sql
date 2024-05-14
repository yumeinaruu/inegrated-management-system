alter table public.schedule
    rename column time to beginning;

alter table public.schedule
    add ending timestamp without time zone not null;