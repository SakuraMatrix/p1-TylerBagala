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
        return Flux.from(session.executeReactive("SELECT name, capital, net_worth FROM market.investors"))
                .map(row -> new Investor(row.getString("name"), row.getDouble("capital"), row.getDouble("net_worth")));
    }

    public Mono<Investor> get(String name) {
        return Mono.from(session.executeReactive("SELECT name, capital, net_worth FROM market.investors WHERE name = " + name))
                .map(row -> new Investor(row.getString("name"), row.getDouble("capital"), row.getDouble("net_worth")));
    }
}