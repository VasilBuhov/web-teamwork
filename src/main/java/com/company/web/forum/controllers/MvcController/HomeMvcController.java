package com.company.web.forum.controllers.MvcController;

import com.company.web.forum.models.FilterTopicOptions;
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

    @GetMapping(value = "/")
    public String showHomePage(Model model) {
        model.addAttribute("topics", topicService.get(new FilterTopicOptions()));
        model.addAttribute("tags", tagService.getTopTags());//returning top 10 most recent tags by creation date
        return "index";
    }
}


