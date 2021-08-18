package com.github.arenia.street_of_fortune.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.github.arenia.street_of_fortune.domain.Investor;

import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class InvestorRepository {
    private CqlSession session;

    public InvestorRepository(CqlSession session) {
        this.session = session;
    }

    public Investor create(Investor investor){
        SimpleStatement statement = SimpleStatement.builder(
            "INSERT INTO market.investors (id, name, net_worth) values (?,?,?,?)")
            .addPositionalValues(
                investor.getId(),
                investor.getName(),
                investor.getNetWorth())
            .build();
        Flux.from(session.executeReactive(statement)).subscribe();
        return investor;
    }

    public Flux<Investor> getAll() {
        return Flux.from(session.executeReactive("SELECT * FROM market.investors"))
            .map(row -> new Investor(row.getInt("id"), row.getString("name"), row.getDouble("net_worth")));
    }

    public Mono<Investor> get(int id) {
        return Mono.from(session.executeReactive("SELECT * FROM market.investors WHERE id = " + id))
            .map(row -> new Investor(row.getInt("id"), row.getString("name"), row.getDouble("net_worth")));
    }
}