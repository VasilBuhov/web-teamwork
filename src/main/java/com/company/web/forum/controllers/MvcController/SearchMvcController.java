package com.company.web.forum.controllers.MvcController;


import com.company.web.forum.models.FilterTopicOptions;
import com.company.web.forum.services.TopicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
@RequestMapping("/search")
public class SearchMvcController {
    private final TopicService topicService;

    public SearchMvcController(TopicService topicService) {
        this.topicService = topicService;
    }
    @GetMapping
    public String searchByTopicName (@RequestParam("search") String title, Model model) {
        System.out.println("String title:" + title);
        FilterTopicOptions filterTopicOptions = new FilterTopicOptions();
        Optional<String> byTitle = Optional.of(title);
        filterTopicOptions.setTitle(byTitle);
        model.addAttribute("topicsByName", topicService.get(filterTopicOptions));
        return "SearchBarView";
    }
}
