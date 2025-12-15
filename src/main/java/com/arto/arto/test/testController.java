package com.arto.arto.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    @GetMapping("/health")
    public String healthCheck() {
        return "LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL";
    }
}
