create table users
(
    id serial primary key,
    first_name      text   not null,
    last_name       text   not null,
    age             integer not null,
    email           text  not null unique
);
create table accounts
(
    id serial primary key,
    balance text not null,
    user_id integer not null references users
);
create table expense_categories
(
    id  serial primary key,
    name_category text not null unique
);

create table expense_categories
(
    id  serial primary key,
    name_category text not null unique
);
create table operations
(
    id  serial primary key,
    date_time timestamp not null,
    result integer not null ,
    account_id integer null references accounts
)

INSERT INTO public.users (id, firstname, lastname, email) VALUES (1, 'Ilya', 'Popov', 'popovilya57@gmail.com');
INSERT INTO public.users (id, firstname, lastname, email) VALUES (2, 'Zhenya', 'Smirnov','evgeniy@gmail.com');
INSERT INTO public.users (id, firstname, lastname, email) VALUES (3, 'Anton', 'Pechkin','anton@gmail.com');
INSERT INTO public.users (id, firstname, lastname, email) VALUES (4, 'Olga', 'Kumpachka','olgaku@gmail.com');

INSERT INTO public.accounts (id, balance, user_id) VALUES (1, 2000, 1);
INSERT INTO public.accounts (id, balance, user_id) VALUES (2, 1500, 2);
INSERT INTO public.accounts (id, balance, user_id) VALUES (3, 3000, 3);
INSERT INTO public.accounts (id, balance, user_id) VALUES (4, 4000, 4);

INSERT INTO public.expense_categories (id, name) VALUES (1, 'Products');
INSERT INTO public.expense_categories (id, name) VALUES (2, 'Cafes and Restaurants');
INSERT INTO public.expense_categories (id, name) VALUES (3, 'Leisure');
INSERT INTO public.expense_categories (id, name) VALUES (4, 'Logistics');
INSERT INTO public.expense_categories (id, name) VALUES (5, 'Purchases');
INSERT INTO public.expense_categories (id, name) VALUES (6, 'Gifts');
INSERT INTO public.expense_categories (id, name) VALUES (7, 'Health');

INSERT INTO public.income_categories (id, name) VALUES (2, 'Salary');
INSERT INTO public.income_categories (id, name) VALUES (3, 'Scholarship');
INSERT INTO public.income_categories (id, name) VALUES (4, 'Gifts');
INSERT INTO public.income_categories (id, name) VALUES (5, 'Pocket money');


