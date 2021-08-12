package com.github.Arenia.street_of_fortune.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import com.github.Arenia.street_of_fortune.domain.Investor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class InvestorRepository {
    private CqlSession session;

    public InvestorRepository(CqlSession session) {
        this.session = session;
    }

    public Flux<Investor> getAll() {
        return Flux.from(session.executeReactive("SELECT * FROM market.investors"))
                .map(row -> new Investor(row.getInt("id"), row.getString("name"), row.getDouble("capital"), row.getDouble("net_worth")));
    }

    public Mono<Investor> get(int id) {
        return Mono.from(session.executeReactive("SELECT * FROM market.investors WHERE id = " + id))
                .map(row -> new Investor(row.getInt("id"), row.getString("name"), row.getDouble("capital"), row.getDouble("net_worth")));
    }
}