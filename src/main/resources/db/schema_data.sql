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

INSERT INTO fruit(id, name)
VALUES (1, 'Cherry');
INSERT INTO fruit(id, name)
VALUES (2, 'Apple');
INSERT INTO fruit(id, name)
VALUES (3, 'Banana');

insert into person(id, name, email, password, birth, active)
VALUES (1, 'Abdul', 'abdul@mail.com', '$2a$12$Z2Zc3tuyh9oiM4GX7EjxmeqVxTtxctl88baFac30XUkU8DCNHhQnm
', '2020-08-16', 1);
insert into person(id, name, email, password, birth, active)
VALUES (2, 'Dewi', 'dewi@mail.com', '$2a$12$Z2Zc3tuyh9oiM4GX7EjxmeqVxTtxctl88baFac30XUkU8DCNHhQnm
', '1996-08-16', 1);
insert into person(id, name, email, password, birth, active)
VALUES (3, 'Azkiya', 'azkiya@mail.com', '$2a$12$Z2Zc3tuyh9oiM4GX7EjxmeqVxTtxctl88baFac30XUkU8DCNHhQnm
', '2020-08-16', 1);
insert into person(id, name, email, password, birth, active)
VALUES (4, 'Syarah', 'syarah@mail.com', '$2a$12$Z2Zc3tuyh9oiM4GX7EjxmeqVxTtxctl88baFac30XUkU8DCNHhQnm
', '2020-08-16', 1);
insert into person(id, name, email, password, birth, active)
VALUES (5, 'Rahman', 'rahman@mail.com', '$2a$12$Z2Zc3tuyh9oiM4GX7EjxmeqVxTtxctl88baFac30XUkU8DCNHhQnm
', '1994-08-16', 1);

insert into role(id, name)
VALUES (1, 'admin');
insert into role(id, name)
VALUES (2, 'super_user');
insert into role(id, name)
VALUES (3, 'user');

insert into person_role(person_id, role_id)
VALUES (1, 1);
insert into person_role(person_id, role_id)
VALUES (2, 1);
insert into person_role(person_id, role_id)
VALUES (1, 2);
insert into person_role(person_id, role_id)
VALUES (1, 3);

insert into shop(id, name)
VALUES (1, 'Shop-001');

insert into product(id, name, quantity, shop_id)
values (1, 'Product-001', 3, 1);
insert into product(id, name, quantity, shop_id)
values (2, 'Product-002', 2, 1);
insert into product(id, name, quantity, shop_id)
values (3, 'Product-003', 1, 1);


