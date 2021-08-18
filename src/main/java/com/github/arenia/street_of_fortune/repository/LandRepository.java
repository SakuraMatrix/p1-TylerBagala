package com.github.arenia.street_of_fortune.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.github.arenia.street_of_fortune.domain.Land;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class LandRepository {
    private static final Logger log =
      (Logger) LoggerFactory.getLogger("cass.land.repo");
    private CqlSession session;

    public LandRepository(CqlSession session) {
        this.session = session;
    }

    public Flux<Land> getAll() {
        log.info("Retrieving all.");
        return Flux.from(session.executeReactive("SELECT * FROM market.lands"))
                .map(row -> new Land(row.getInt("id"), row.getString("name"),
                 row.getDouble("value"), row.getDouble("shop_price")));
    }

    public Mono<Land> get(int id) {
        log.info("Retrieving one.");
        return Mono.from(session.executeReactive("SELECT * FROM market.lands WHERE id = " + id))
            .map(row -> new Land(row.getInt("id"), row.getString("name"),
            row.getDouble("value"), row.getDouble("shop_price")));
    }

    public Land create(Land land) {
        log.info("Creating a new land.");
        SimpleStatement statement = SimpleStatement.builder(
            "INSERT INTO market.lands (id, name, value, shop_price) values (?,?,?,?)")
            .addPositionalValues(
                land.getId(),
                land.getName(),
                land.getValue(),
                land.getShopPrice())
            .build();
        Flux.from(session.executeReactive(statement)).subscribe();
        return land;
    }
}