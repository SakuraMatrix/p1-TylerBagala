package com.github.Arenia.street_of_fortune;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import com.datastax.oss.driver.api.core.CqlSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.Arenia.street_of_fortune.repository.InvestorRepository;
import com.github.Arenia.street_of_fortune.repository.PropertyRepository;
import com.github.Arenia.street_of_fortune.service.InvestorService;
import com.github.Arenia.street_of_fortune.service.PropertyService;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

public class App {
    public static void main(String[] args) throws URISyntaxException {
        runNetty();
    }

    private static void runNetty() throws URISyntaxException { 
        
        CqlSession session = CqlSession.builder().build();
        InvestorRepository investorRepository = new InvestorRepository(session);
        InvestorService investorService = new InvestorService(investorRepository);
        PropertyRepository propertyRepository = new PropertyRepository(session);
        PropertyService propertyService = new PropertyService(propertyRepository);


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
                .get("/properties", (request, response) ->
                    response.send(propertyService.getAll().map(App::toByteBuf)
                    .log("http-server")))
                )
        .bindNow()
        .onDispose()
        .block();
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