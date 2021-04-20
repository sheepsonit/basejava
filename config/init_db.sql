create table resume
(
    uuid      char(36) not null
        constraint resume_pk
            primary key,
    full_name text not null
);

create table contact
(
    id          serial   not null
        constraint contact_pk
            primary key,
    resume_uuid char(36) not null
        constraint contact_resume_uuid_fk
            references resume
            on update restrict on delete cascade,
    type        text     not null,
    value       text     not null
);

create unique index contact_uuid_type_index
    on contact (resume_uuid, type);

create table section
(
    type_id     serial   not null
        constraint section_type_pk
            primary key,
    type        text,
    value       text,
    resume_uuid char(36) not null
        constraint section_resume_uuid_fk
            references resume
            on update restrict on delete cascade
);

create unique index section_type_type_type_id_index
    on section (type, type_id);