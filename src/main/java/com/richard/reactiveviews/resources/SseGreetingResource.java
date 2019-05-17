package com.richard.reactiveviews.resources;

import com.richard.reactiveviews.model.Greeting;
import com.richard.reactiveviews.produces.GreetingProducer;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SseGreetingResource {

    private final GreetingProducer producer;

    @GetMapping(value = "/sse/greetings/{name}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<String> greet(@PathVariable String name) {
        return this.producer
                .greet(name)
                .map(Greeting::getMessage);
    }

}
