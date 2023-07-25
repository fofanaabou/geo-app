package com.aws.geoapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping(HelloController.BASE_URL)
@RestController
public class HelloController {
    public static final String BASE_URL = "/geo-app";

    @GetMapping(value = "/hello", produces = "application/json")
    public Mono<String> hello() {
        return Mono.just("Hello World");
    }
}
