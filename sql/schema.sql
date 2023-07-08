
--drop  table if exists categories, product, characteristic, characteristic_value, discount, discount_product;

create table categories
(
    id serial8,
    name varchar not null,
    primary key (id)
);

create table product
(
    id serial8,
    name varchar not null,
    categories_id int8,
    price int8 not null ,
    primary key (id),
    FOREIGN KEY (categories_id) REFERENCES categories(id)
);

create table characteristic
(
    id serial8,
    name varchar not null,
    categories_id int8,
    primary key (id),
    FOREIGN KEY (categories_id) REFERENCES categories(id)
);

create table characteristic_value
(
    id serial8,
    name varchar not null,
    product_id int8,
    characteristic_id int8,
    primary key (id),
    FOREIGN KEY (characteristic_id) REFERENCES characteristic(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

create table discount
(
    id serial8,
    name varchar not null ,
    percent int8 not null ,
    primary key (id)
);

create table discount_product
(
    categories_id int8 not null ,
    discount_id int8 not null ,
    primary key (categories_id, discount_id),
    foreign key (categories_id) references categories(id),
    foreign key (discount_id) references  discount (id)
);
