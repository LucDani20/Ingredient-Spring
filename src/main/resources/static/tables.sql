--TD2
create type dish_type as enum ('STARTER', 'MAIN', 'DESSERT');


create table dish
(
    id        serial primary key,
    name      varchar(255),
    dish_type dish_type
);

create type ingredient_category as enum ('VEGETABLE', 'ANIMAL', 'MARINE', 'DAIRY', 'OTHER');

create table ingredient
(
    id       serial primary key,
    name     varchar(255),
    price    numeric(10, 2),
    category ingredient_category,
    id_dish  int references dish (id)
);


--TD3
create type unit_type as enum('PCS', 'KG', 'L');

create table DishIngredients(
    id serial primary key ,
    id_dish int references dish(id),
    id_ingredient int references ingredient(id),
    quantity_required numeric(10,2 ),
    unit unit_type
);

alter table ingredient drop column  id_dish;

alter table dish
    add column if not exists price numeric(10, 2);

--TD4
create type movement_type as enum('IN', 'OUT');

create table StockMovement(
    id serial primary key,
    id_ingredient int not null references ingredient(id),
    quantity numeric(10,2) not null,
    type movement_type default 'OUT',
    unit unit_type default 'KG',
    creation_datetime timestamp default now()
);