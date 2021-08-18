CREATE KEYSPACE IF NOT EXISTS market WITH REPLICATION = {'class' : 'SimpleStrategy', 'replication_factor' : '1'};

CREATE TABLE IF NOT EXISTS market.investors (
    id int PRIMARY KEY,
    name text,
    net_worth double,
    stocks map<text, int>
);

CREATE TABLE IF NOT EXISTS market.lands (
    id int PRIMARY KEY,
    name text,
    value double,
    shop_price double,
);

INSERT into market.investors (id, name, net_worth) values (1, 'Anna', 1500);
INSERT into market.investors (id, name, net_worth) values (2, 'Benny', 1500);

INSERT into market.lands (id, name, value, shop_price)
values(1, 'Grocery Store', 500.00, 20.00);
INSERT into market.lands (id, name, value, shop_price)
values(2, 'Jewler', 1000.00, 100.00);
INSERT into market.lands (id, name, value, shop_price)
values(3, 'Metal Refinery', 300.00, 15.00);