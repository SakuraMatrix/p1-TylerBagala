CREATE KEYSPACE IF NOT EXISTS market WITH REPLICATION = {'class' : 'SimpleStrategy', 'replication_factor' : '1'};

CREATE TABLE IF NOT EXISTS market.investors (
    name text PRIMARY KEY,
    capital double,
    net_worth double,
    stocks map<text, int>
);

CREATE TABLE IF NOT EXISTS market.properties (
    id int,
    name text,
    value double,
    shop_price double,
    max_investment double,
    owner text,
    district_name text,
    stock_value double,
    PRIMARY KEY (id, district_name)
);

INSERT into market.investors (name, capital, net_worth) values ('Anna', 1500, 1500);
INSERT into market.investors (name, capital, net_worth) values ('Benny', 1500, 1500);