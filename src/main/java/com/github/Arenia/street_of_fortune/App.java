package com.github.Arenia.street_of_fortune;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.Arenia.street_of_fortune.repository.InvestorRepository;
import com.github.Arenia.street_of_fortune.service.InvestorService;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

public class App {
    public static void main(String[] args) throws URISyntaxException {
        CqlSession session = CqlSession.builder().build();
        InvestorRepository investorRepository = new InvestorRepository(session);
        InvestorService investorService = new InvestorService(investorRepository);

        runNetty(investorService);
    }

    private static void runNetty(InvestorService investorService) throws URISyntaxException {     
        HttpServer.create()
        .port(8080)
        .route(routes ->
            routes.get("/", (request, response) ->
                    response.sendString(Mono.just("Hello World!")
                            .log("http-server")))
                .post("/echo", (request, response) ->
                    response.send(request.receive().retain()
                            .log("http-server")))
                .get("/investors", (request, response) ->
                    response.send(investorService.getAll().map(App::toByteBuf)
                    .log("http-server")))
                )
        .bindNow()
        .onDispose()
        .block();
    }

    private static void runCass(){
        try (CqlSession session = CqlSession.builder().build()) {

            Select query = QueryBuilder.selectFrom("system", "local").column("release_version"); // SELECT release_version FROM system.local
            SimpleStatement statement = query.build();

            ResultSet rs = session.execute(statement);
            List<Row> rows = rs.all();
            for(Row row : rows){System.out.println(row.getString("release_version"));}
			System.out.println("READ HERE READ HERE READ HERE");
        }
    }

    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static ByteBuf toByteBuf(Object o){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            OBJECT_MAPPER.writeValue(out, o);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ByteBufAllocator.DEFAULT.buffer().writeBytes(out.toByteArray());
    }
}