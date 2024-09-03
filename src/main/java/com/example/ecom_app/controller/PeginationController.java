package com.example.ecom_app.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "/api/")
public class PeginationController {

    @GetMapping("public/pegination")
    public String getPegnation(
            @RequestParam(name = "message", defaultValue = "default message", required = true) String message) {
        return "message is " + message;
    }

}
