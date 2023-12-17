package com.gloomhaven.helper.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class Test {
    @GetMapping
    public ResponseEntity<String> authTest(){
        return ResponseEntity.ok("You are authenticated");
    }
}
