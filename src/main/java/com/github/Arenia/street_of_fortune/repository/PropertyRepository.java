package com.github.Arenia.street_of_fortune.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import com.github.Arenia.street_of_fortune.domain.Property;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class PropertyRepository {
    private CqlSession session;

    public PropertyRepository(CqlSession session) {
        this.session = session;
    }

    public Flux<Property> getAll() {
        return Flux.from(session.executeReactive("SELECT * FROM market.properties"))
                .map(row -> new Property(row.getInt("id"), row.getString("name"), row.getDouble("value"),
                 row.getDouble("max_investment"), row.getDouble("shop_price"), row.getString("owner"),
                  row.getString("district_name"), row.getDouble("stock_value")));
    }

    public Mono<Property> get(int id) {
        return Mono.from(session.executeReactive("SELECT * FROM market.properties WHERE id = " + id))
                .map(row -> new Property(row.getInt("id"), row.getString("name"), row.getDouble("value"),
                row.getDouble("max_investment"), row.getDouble("shop_price"), row.getString("owner"),
                 row.getString("district_name"), row.getDouble("stock_value")));
    }
}