package com.github.arenia.street_of_fortune;

import com.datastax.oss.driver.api.core.CqlSession;
import com.github.arenia.street_of_fortune.service.*;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

@Configuration
@ComponentScan
public class AppConfig {
    @Autowired InvestorService investorService;
    @Autowired PropertyService propertyService;
    
    @Bean
    public CqlSession session() {
        return CqlSession.builder().build();
    }


    @Bean
    public DisposableServer disposableServer() {
        return HttpServer.create()
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
                .get("/investors/{param}", (request, response) ->
                    response.send(investorService.get(request.param("param")).map(App::toByteBuf)
                    .log("http-server")))
                .get("/properties", (request, response) ->
                    response.send(propertyService.getAll().map(App::toByteBuf)
                    .log("http-server")))
                .get("/properties/{param}", (request, response) ->
                    response.send(propertyService.get(request.param("param")).map(App::toByteBuf)
                    .log("http-server")))
                )
        .bindNow();
    }
}
