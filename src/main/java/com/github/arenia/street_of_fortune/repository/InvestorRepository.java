package com.github.arenia.street_of_fortune.repository;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.update.Update;
import com.github.arenia.street_of_fortune.domain.Investor;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ch.qos.logback.classic.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class InvestorRepository {
    private static final Logger log =
      (Logger) LoggerFactory.getLogger("cass.investor.repo");
    private CqlSession session;

    public InvestorRepository(CqlSession session) {
        this.session = session;
    }

    public Investor create(Investor investor){
        log.info("Adding an investor.");
        SimpleStatement statement = SimpleStatement.builder(
            "INSERT INTO market.investors (id, name, net_worth) values (?,?,?)")
            .addPositionalValues(
                investor.getId(),
                investor.getName(),
                investor.getNetWorth())
            .build();
        Flux.from(session.executeReactive(statement)).subscribe();
        return investor;
    }

    public Flux<Investor> getAll() {
        log.info("Retrieving all.");
        return Flux.from(session.executeReactive("SELECT * FROM market.investors"))
            .map(row -> new Investor(row.getInt("id"), row.getString("name"), row.getDouble("net_worth")));
    }

    public Mono<Investor> get(int id) {
        log.info("Retrieving one investor.");
        return Mono.from(session.executeReactive("SELECT * FROM market.investors WHERE id = " + id))
            .map(row -> new Investor(row.getInt("id"), row.getString("name"), row.getDouble("net_worth")));
    }

    public Mono<Integer> deleteInvestor(int id){
        log.info("Attempting delete of investor.");
        Flux.from(
            session.executeReactive(
                SimpleStatement.builder("DELETE FROM market.investors WHERE id = ?")
                    .addPositionalValue(id)
                    .build()
            )
        ).subscribe();
        return Mono.just(id);
    }

    public Double updateWorth(int id, double newWorth) {
        log.info("Updating Net worth.");

        Update update = QueryBuilder.update("market", "investors")
            .setColumn("net_worth", QueryBuilder.bindMarker())
            .whereColumn("id")
            .isEqualTo(QueryBuilder.bindMarker());

        Mono.from(
            session.executeReactive(
                session.prepare(update.build()).bind().setDouble(0, newWorth).setInt(1, id)))
        .subscribe();
        return newWorth;
    }
}