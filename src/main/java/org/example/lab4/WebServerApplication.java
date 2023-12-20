package org.example.lab4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebServerApplication {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(WebServerApplication.class, args);
    }
}