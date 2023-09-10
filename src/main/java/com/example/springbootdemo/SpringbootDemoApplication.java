package com.example.springbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
@RestController
public class SpringbootDemoApplication {
    private final AtomicLong counter = new AtomicLong();

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoApplication.class, args);
    }

    @GetMapping("/ping")
    public String ping(@RequestParam(value = "name", defaultValue = "") String name) {
        return name.isBlank() ? "Pong!" : String.format("Yo, we got ping from %s!", name);
    }

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "") String name) {
        String template = ("Welcome Sir %s.");

        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
