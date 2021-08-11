# p1-TylerBagala
Streets of Fortune: A server API to keep track of a simulated retail market.

# Proposal
SoF is an API that covers and tracks a retail market of various shops and their investors. Investors can invest money into their properties and increase their value and shop price, while getting data on properties such as their current total worth, shop prices, and shop owner. These properties also belong to districts, which have their own designation to observe what properties are in each district including the total worth and average shop value of the district. Additionally investors can get information on the other investors, such as total worth, current available cash, and what properties they control.

# Users can post queries to
1)	Have an investor invest into their property, spending their available cash to invest into the property and raise its value and shop price.
2)	Request a payday for an investor, giving them a sum of cash to work with.
3)	Get an investor to buy out a property, taking over the ownership of the property.
4)	Make an investor shop at a property, giving their cash to the owner of the shop.
5)	Encourage an investor to buy or sell stocks in a district, updating their cash and stock count as well as potentially altering the stock price.

#  RESTful queries
1) /properties gets a list of all properties in the market.
2) /properties/{id} gets a more detailed view of a single property, such as current investment, prices, owner, and maximum value.
3) /districts gets a list of current districts, as well as the current value of the stock in said district.
4) /districts/{id} shows a detailed view of a district, such as the properties in the given district and the current stock investments and price of stock.
5) /investors gets a general view of the current investors, such as their current cash and net worth.
6) /investors/{id} shows a detailed view of the investor, such as their overall worth, owned properties, held stocks, and available capital.
7) /stocks shows the general stock market, showing prices for each district as well as current holdings.


# Technology Stack
1) Java 8
2) Maven 
3) Junit
4) SLF4J: Logback Classic
5) Apache Cassandra w/ Datastax driver
6) GitHub (SakuraMatrix)
7) Reactor Netty 
8) Spring Framework

# Building
...

# Usage
...
