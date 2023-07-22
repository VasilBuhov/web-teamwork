package com.company.web.forum.controllers.MvcController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeMvcController {
    @GetMapping
    public String showHomePage() {
        return "HomeView";
    }
}
