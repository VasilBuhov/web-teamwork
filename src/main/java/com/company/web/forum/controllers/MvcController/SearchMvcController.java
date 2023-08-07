package com.company.web.forum.controllers.MvcController;


import com.company.web.forum.models.FilterTopicOptions;
import com.company.web.forum.services.TagService;
import com.company.web.forum.services.TopicService;
import com.company.web.forum.services.UserService;
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
    private final UserService userService;

    private final TagService tagService;
    public SearchMvcController(TopicService topicService, UserService userService, TagService tagService) {
        this.topicService = topicService;
        this.userService = userService;
        this.tagService = tagService;
    }
    @GetMapping
    public String searchByTopicName (@RequestParam("search") String title, Model model) {
        FilterTopicOptions filterTopicOptions = new FilterTopicOptions();
        Optional<String> byTitle = Optional.of(title);
        filterTopicOptions.setTitle(byTitle);
        model.addAttribute("topicsByName", topicService.get(filterTopicOptions));
        FilterTopicOptions noFilterTopicOptions = new FilterTopicOptions();
        model.addAttribute("allTopics", topicService.get(noFilterTopicOptions));
        model.addAttribute("countUsers", userService.getUsersCount());
        model.addAttribute("topTags", tagService.getTopTags());
        return "SearchBarView";
    }
}
