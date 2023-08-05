package com.company.web.forum.controllers.MvcController;


import com.company.web.forum.models.FilterTopicOptions;
import com.company.web.forum.services.TopicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;


@Controller
@RequestMapping("/search")
public class SearchMvcController {
    private final TopicService topicService;

    public SearchMvcController(TopicService topicService) {
        this.topicService = topicService;
    }
    @GetMapping("/{title}")
    public String searchByTopicName (@PathVariable String title, Model model) {
        FilterTopicOptions filterTopicOptions = new FilterTopicOptions();
        Optional<String> byTitle = Optional.of(title);
        filterTopicOptions.setTitle(byTitle);
        model.addAttribute("topicsByName", topicService.get(filterTopicOptions));

        return "SearchBarView";
    }
}
