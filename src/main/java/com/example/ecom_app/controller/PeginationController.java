package com.example.ecom_app.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.ecom_app.model.Category;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/")
public class PeginationController {
    List<String> names = new ArrayList<>();

    public PeginationController() {
        for (int i = 0; i < 100; i++) {
            names.add("streaming" + i);
        }
    }

    @GetMapping("public/pegination")
    public Map<String, String> getPegnation() {
        Map<String, String> peginationResp = new HashMap<>();
        peginationResp.put("content", "names");
        peginationResp.put("totalElements", String.valueOf(names.size()));
        return peginationResp;
    }

}
