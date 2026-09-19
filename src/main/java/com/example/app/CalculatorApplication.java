package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalculatorApplication.class, args);
    }

    @GetMapping("/")
    public String home() {
        return "Jenkins CI/CD Demo Application";
    }

    @GetMapping("/health")
    public String health() {
        return "UP";
    }

    @GetMapping("/add")
    public String add() {
        return "10 + 20 = " + (10 + 20);
    }
}