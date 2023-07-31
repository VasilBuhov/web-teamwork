package com.company.web.forum.controllers.MvcController;

import com.company.web.forum.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeMvcController {

    private final TagService tagService;
    @Autowired
    public HomeMvcController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/")
    public String showHomePage(Model model) {

        model.addAttribute("tags", tagService.getAllTags(1, 10));
        return "index";
    }
}


