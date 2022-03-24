package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/aop")
public class HomeController {

    @GetMapping(value = "/1")
    public void get1() {
    }

    @GetMapping(value = "/2")
    public void get2() {
    }

    @GetMapping("/")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("home page", HttpStatus.OK);
    }

}
