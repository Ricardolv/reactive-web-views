package com.richard.reactiveviews.controllers;

import com.richard.reactiveviews.produces.GreetingProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

@Controller
@RequiredArgsConstructor
public class ThymeleafViewGreetingsController {

    private final GreetingProducer producer;

    @GetMapping("/greetings.do")
    String renderInitialView() {
        return "greetings";
    }

    @GetMapping(value = "/greetings-updates.do", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    String renderUpdatedView (@RequestParam ("name") String name,  Model model) {

        ReactiveDataDriverContextVariable variable = new ReactiveDataDriverContextVariable(this.producer.greet(name), 1);
        model.addAttribute("greetings", variable);
        return "greetings :: #greetigns-block";
    }
}
