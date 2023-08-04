package com.company.web.forum.controllers.MvcController;


import com.company.web.forum.services.PostService;
import com.company.web.forum.services.TagService;
import com.company.web.forum.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeMvcController {

    private final TagService tagService;
    private final TopicService topicService;
    @Autowired
    public HomeMvcController(TagService tagService, TopicService topicService) {
        this.tagService = tagService;
        this.topicService = topicService;
    }

    @GetMapping
    public String showHomePage(Model model) {
        model.addAttribute("topicsByDate", topicService.get10("desc", "creation date"));
        model.addAttribute("topicsByPosts", topicService.get10("desc", "posts"));
        model.addAttribute("topicsByLikes", topicService.get10("desc", "likes"));
        model.addAttribute("topicsByViews", topicService.get10("desc", "views"));
        model.addAttribute("tags", tagService.getTopTags());
        return "index";
    }

    @GetMapping(value = "/contact_us")
    public String showContactUs(Model model) {
        model.addAttribute("tags", tagService.getTagById(9));
        return "contact_us";
    }

    @GetMapping(value = "/login")
    public String showLogIn(Model model) {
        model.addAttribute("tags", tagService.getTagById(9));
        return "log_in";
    }
}


