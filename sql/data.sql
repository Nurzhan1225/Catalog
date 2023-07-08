insert into categories(name)
values ('Процессоры'),
       ('Мониторы');

insert into product (name, categories_id, price)
values (' Intel Core I9 9900',1, 100),
       ('AMD Ryzen R7 7700',1, 150),
       ('Samsung SU556270',2, 200),
       ('AOC Z215S659',2, 250);

insert into characteristic (name, categories_id)
values ('Производитель',1),
       ('Количество ядер',1),
       ('Сокет',1),
       ('Производитель',2),
       ('Диагональ',2),
       ('Матрица',2),
       ('Разрешение',2);

insert into characteristic_value (name, product_id, characteristic_id)
values ('Intel',1,1),
       ('AMD',2,1),
       ('8',1,2),
       ('12',2,2),
       ('1250',1,3),
       ('AM4',2,3),
       ('Samsung',3,4),
       ('AOC',4,4),
       ('27',3,5),
       ('21.5',4,5),
       ('TN',3,6),
       ('AH-IPS',4,6),
       ('2560*1440',3,7),
       ('1920*1080',4,7);


insert into discount (name, percent)
VALUES ('золото', 50),
       ('серебро', 30);

insert into discount_product
(categories_id, discount_id) VALUES (1,1),
                                    (2,1),
                                    (1,2),
                                    (2,2);
