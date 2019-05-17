package com.richard.reactiveviews.config;

import com.richard.reactiveviews.model.Greeting;
import com.richard.reactiveviews.produces.GreetingProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Configuration
public class WebSocketConfig {

    SimpleUrlHandlerMapping simpleUrlHandlerMapping(WebSocketHandler wsh) {
        return new SimpleUrlHandlerMapping() {
            {
                setOrder(10);
                setUrlMap(Map.of("/ws/greetings", wsh));
            }
        };
    }

    @Bean
    WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    // Sintaxe Fuc.
    @Bean
    WebSocketHandler webSocketHandler(GreetingProducer producer) {
        return session -> {
            var greetings = session
                    .receive()
                    .map(WebSocketMessage::getPayloadAsText)
                    .flatMap(producer::greet)
                    .map(Greeting::getMessage)
                    .map(session::textMessage);
            return session.send(greetings);
        };
    }

//    @Bean
//    WebSocketHandler webSocketHandler(GreetingProducer producer) {
//        return new WebSocketHandler() {
//
//            @Override
//            public Mono<Void> handle(WebSocketSession session) {
//                Flux<String> namesToGreel = session.receive().map(wsm -> wsm.getPayloadAsText());
//
//                Flux<Greeting> responseGreetings = namesToGreel.flatMap(nameToGreel -> producer.greet(nameToGreel));
//
//                Flux<String> stringResponses = responseGreetings.map(responseGreeting -> responseGreeting.getMessage());
//
//                Flux<WebSocketMessage> map = stringResponses.map(stringResponse -> session.textMessage(stringResponse));
//                return session.send(map);
//            }
//        };
//    }


}
