package deu.movietalk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController  {
    @GetMapping("/user")
    public String Test() {
        return "hello";
    }
}