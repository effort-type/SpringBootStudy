package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi") // hi라는 url입력이 들어오면 greetings 페이지를 반환해줌
    public String niceToMeetYou(Model model){
        model.addAttribute("username", "민수");
        return "greetings"; // templates/greetings.mustache 파일을 찾아서 브라우저로 전송
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("nickname", "정민수");
        return "goodbye";
    }
}
