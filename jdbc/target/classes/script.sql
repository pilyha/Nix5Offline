create table locations
(
    id serial not null primary key,
    name text not null unique
);

create table if not exists problems
(
    id serial not null  primary key,
    from_id integer references locations,
    to_id integer references locations,
    unique (from_id, to_id)
);

create table routes
(
    id serial not null  primary key,
    from_id integer references locations,
    to_id integer references locations,
    cost integer
);

create table solutions
(
    problem_id integer not null  primary key references problems,
    cost integer
);
INSERT INTO locations (id, name) VALUES (1, 'Gdansk');
INSERT INTO locations (id, name) VALUES (2, 'Bydgoszcz');
INSERT INTO locations (id, name) VALUES (3, 'Torun');
INSERT INTO locations (id, name) VALUES (4, 'Warszawa');

INSERT INTO routes (id, from_id, to_id, cost) VALUES (1, 1, 2, 1);
INSERT INTO routes (id, from_id, to_id, cost) VALUES (2, 1, 3, 3);
INSERT INTO routes (id, from_id, to_id, cost) VALUES (3, 2, 4, 4);
INSERT INTO routes (id, from_id, to_id, cost) VALUES (4, 2, 3, 1);
INSERT INTO routes (id, from_id, to_id, cost) VALUES (5, 2, 1, 1);
INSERT INTO routes (id, from_id, to_id, cost) VALUES (6, 3, 1, 3);
INSERT INTO routes (id, from_id, to_id, cost) VALUES (7, 3, 2, 1);
INSERT INTO routes (id, from_id, to_id, cost) VALUES (8, 3, 4, 1);
INSERT INTO routes (id, from_id, to_id, cost) VALUES (9, 4, 2, 4);
INSERT INTO routes (id, from_id, to_id, cost) VALUES (10, 4, 3, 1);

INSERT INTO problems (id, from_id, to_id) VALUES (1, 1, 4);
INSERT INTO problems (id, from_id, to_id) VALUES (2, 2, 4);
INSERT INTO problems (id, from_id, to_id) VALUES (3, 1, 3);