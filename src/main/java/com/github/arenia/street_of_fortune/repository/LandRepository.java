package com.github.arenia.street_of_fortune.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.github.arenia.street_of_fortune.domain.Land;

import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class LandRepository {
    private CqlSession session;

    public LandRepository(CqlSession session) {
        this.session = session;
    }

    public Flux<Land> getAll() {
        return Flux.from(session.executeReactive("SELECT * FROM market.lands"))
                .map(row -> new Land(row.getInt("id"), row.getString("name"),
                 row.getDouble("value"), row.getDouble("shop_price")));
    }

    public Mono<Land> get(int id) {
        return Mono.from(session.executeReactive("SELECT * FROM market.lands WHERE id = " + id))
            .map(row -> new Land(row.getInt("id"), row.getString("name"),
            row.getDouble("value"), row.getDouble("shop_price")));
    }

    public Land create(Land land) {
        SimpleStatement statement = SimpleStatement.builder(
            "INSERT INTO market.lands (id, name, shop_price, max_investment, owner, district_name, stock_value) values (?,?,?,?,?,?,?)")
            .addPositionalValues(
                land.getId(),
                land.getName(),
                land.getShopPrice())
            .build();
        Flux.from(session.executeReactive(statement)).subscribe();
        return land;
    }
}