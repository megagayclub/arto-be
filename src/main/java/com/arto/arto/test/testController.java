package com.arto.arto.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    @GetMapping("/health")
    public String healthCheck() {
<<<<<<< Updated upstream
        return "변경테스트";
=======
        return "변경테스트123124123123";
>>>>>>> Stashed changes
    }
}
