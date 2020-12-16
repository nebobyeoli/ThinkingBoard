package dimigo.hee.ThinkingBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController
{
    @GetMapping("/") // root!
    public String home() // 메인 페이지 보여줌
    {
        return "home";
    }
}
