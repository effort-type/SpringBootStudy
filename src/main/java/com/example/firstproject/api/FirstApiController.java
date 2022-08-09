package com.example.firstproject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //  RestAPI용 컨트롤러, 일반적인 controller는 view 페이지를 반환하지만 RestController는 json을 반환
public class FirstApiController {

    @GetMapping("/api/hello")
    public String hello() {
        return "hello world";
    }
}
