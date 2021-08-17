package com.github.arenia.street_of_fortune;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import reactor.netty.DisposableServer;

public class App {
    public static void main(String[] args) throws URISyntaxException {
        runNetty();
    }

    private static void runNetty() throws URISyntaxException { 
        
        AnnotationConfigApplicationContext applicationContext =
            new AnnotationConfigApplicationContext(AppConfig.class);


        applicationContext.getBean(DisposableServer.class)
        .onDispose()
        .block();

        applicationContext.close();
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