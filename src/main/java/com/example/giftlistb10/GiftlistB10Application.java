package com.example.giftlistb10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class  GiftlistB10Application {
    public static void main(String[] args) {
        SpringApplication.run(GiftlistB10Application.class, args);
    }
    @GetMapping
    public String greetings(){
        return "index";
    }
}