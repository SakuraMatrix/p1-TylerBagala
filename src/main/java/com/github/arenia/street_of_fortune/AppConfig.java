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
    @Autowired LandService landService;
    
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
                .post("/investors", (request, response) ->
                    response.send(request.receive()
                        .asString()
                        .map(App::parseInvestor)
                        .map(investorService::newInvestor)
                        .map(App::toByteBuf)
                        .log("http-server")))
                .get("/investors/{id}", (request, response) ->
                    response.send(investorService.get(request.param("id")).map(App::toByteBuf)
                        .log("http-server")))
                .get("/investors/delete/{id}", (request, response) ->
                    response.send(
                        investorService.deleteInvestor(request.param("id"))
                        .map(App::toByteBuf)
                        .log("http-server")
                    ))
                .get("/investors/{id}/{newWorth}", (request, response) ->
                    response.send(
                        Mono.just(investorService.updateWorth(request.params()))
                        .map(App::toByteBuf)
                    ))
                .get("/lands", (request, response) ->
                    response.send(landService.getAll().map(App::toByteBuf)
                        .log("http-server")))
                .post("/lands", (request, response) ->
                    response.send(request.receive()
                        .asString()
                        .map(App::parseLand)
                        .map(landService::newLand)
                        .map(App::toByteBuf)
                        .log("http-server")))
                .get("/lands/delete/{id}", (request, response) ->
                        response.send(
                            landService.deleteLand(request.param("id"))
                            .map(App::toByteBuf)
                            .log("http-server")
                        ))
                .get("/lands/{id}", (request, response) ->
                    response.send(landService.get(request.param("id")).map(App::toByteBuf)
                        .log("http-server")))
                .get("/lands/{id}/{newprice}", (request, response) ->
                    response.send(
                        Mono.just(landService.updateShopPrice(request.params()))
                        .map(App::toByteBuf)
                    )
                )
            )
        .bindNow();
    }
}
