DELETE FROM fruit WHERE 1=1;
INSERT INTO fruit(id, name)
VALUES (1, 'Cherry');
INSERT INTO fruit(id, name)
VALUES (2, 'Apple');
INSERT INTO fruit(id, name)
VALUES (3, 'Banana');

DELETE FROM person WHERE 1=1;
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

DELETE FROM role WHERE 1=1;
insert into role(id, name)
VALUES (1, 'admin');
insert into role(id, name)
VALUES (2, 'super_user');
insert into role(id, name)
VALUES (3, 'user');

DELETE FROM person_role WHERE 1=1;
insert into person_role(person_id, role_id)
VALUES (1, 1);
insert into person_role(person_id, role_id)
VALUES (2, 1);
insert into person_role(person_id, role_id)
VALUES (1, 2);
insert into person_role(person_id, role_id)
VALUES (1, 3);

DELETE FROM shop WHERE 1=1;
insert into shop(id, name)
VALUES (1, 'Shop-001');

DELETE FROM product WHERE 1=1;
insert into product(id, name, quantity, shop_id)
values (1, 'Product-001', 3, 1);
insert into product(id, name, quantity, shop_id)
values (2, 'Product-002', 2, 1);
insert into product(id, name, quantity, shop_id)
values (3, 'Product-003', 1, 1);


