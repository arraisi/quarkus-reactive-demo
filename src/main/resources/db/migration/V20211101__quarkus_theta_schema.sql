create table fruit
(
    id   bigint not null,
    name varchar(255),
    primary key (id)
) engine = InnoDB;

create table person
(
    id       bigint not null,
    name     varchar(255),
    birth    date,
    email    varchar(255),
    password varchar(255),
    active   bit(1),
    primary key (id)
) engine = InnoDB;

create table role
(
    id        bigint not null,
    name      varchar(255),
    person_id bigint,
    primary key (id)
) engine = InnoDB;

create table person_role
(
    person_id bigint not null,
    role_id   bigint not null,
    primary key (person_id, role_id)
) engine = InnoDB;

alter table person_role
    add constraint pr_person_id_fk
        foreign key (person_id) references person (id);

alter table person_role
    add constraint pr_role_id_fk
        foreign key (role_id) references role (id);

create table shop
(
    id   bigint       not null,
    name varchar(255) null,
    primary key (id)
) engine = InnoDB;

create table product
(
    id       bigint not null,
    name     varchar(255),
    quantity int    null,
    shop_id  bigint not null,
    primary key (id)
) engine = InnoDB;

alter table product
    add constraint product_shop_id_fk
        foreign key (shop_id) references shop (id);