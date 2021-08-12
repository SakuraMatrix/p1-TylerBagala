CREATE KEYSPACE IF NOT EXISTS market WITH REPLICATION = {'class' : 'SimpleStrategy', 'replication_factor' : '1'};

CREATE TABLE IF NOT EXISTS market.investors (
    id int PRIMARY KEY,
    name text,
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

INSERT into market.investors (id, name, capital, net_worth) values (1, 'Anna', 1500, 1500);
INSERT into market.investors (id, name, capital, net_worth) values (2, 'Benny', 1500, 1500);

INSERT into market.properties (id, name, value, shop_price, max_investment, owner, district_name, stock_value)
values(1, 'Grocery Store', 500.00, 20.00, 1000.00, 'none', 'Commercial', 16.00);
INSERT into market.properties (id, name, value, shop_price, max_investment, owner, district_name, stock_value)
values(2, 'Jewler', 1000.00, 100.00, 2000.00, 'Anna', 'Luxury', 20.00);
INSERT into market.properties (id, name, value, shop_price, max_investment, owner, district_name, stock_value)
values(3, 'Metal Refinery', 300.00, 15.00, 500.00, 'Benny', 'Industrial', 12.00);