package com.github.arenia.street_of_fortune;

import ch.qos.logback.classic.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.arenia.street_of_fortune.domain.Investor;
import com.github.arenia.street_of_fortune.domain.Land;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import reactor.netty.DisposableServer;

public class App {
    private static final Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("app");
    public static void main(String[] args) throws URISyntaxException {
        log.info("Starting up.");
        runNetty();
    }

    private static void runNetty() throws URISyntaxException { 
        log.info("Bean creation...");
        AnnotationConfigApplicationContext applicationContext =
            new AnnotationConfigApplicationContext(AppConfig.class);

        log.info("Server windup...");
        applicationContext.getBean(DisposableServer.class)
        .onDispose()
        .block();

        applicationContext.close();
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static ByteBuf toByteBuf(Object o){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            OBJECT_MAPPER.writeValue(out, o);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ByteBufAllocator.DEFAULT.buffer().writeBytes(out.toByteArray());
    }

    public static ByteBuf doubleToByteBuff(Object o) {
        try {
          return Unpooled.buffer()
              .writeBytes(OBJECT_MAPPER.writerFor(Double.class).writeValueAsBytes(o));
        } catch (JsonProcessingException e) {
          e.printStackTrace();
        }
        return null;
      }

    public static Investor parseInvestor(String str){
        Investor investor = null;
        try{
            investor = OBJECT_MAPPER.readValue(str, Investor.class);
        } 
        catch (IOException ex){
            ex.printStackTrace();
        }
        return investor;
    }

    public static Land parseLand(String str){
        Land land = null;
        try{
            land = OBJECT_MAPPER.readValue(str, Land.class);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return land;
    }
}