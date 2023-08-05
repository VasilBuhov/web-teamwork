package com.company.web.forum.controllers.MvcController;

import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.helpers.PostMapper;
import com.company.web.forum.models.*;
import com.company.web.forum.services.PostService;
import com.company.web.forum.services.TopicService;
import com.company.web.forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/topics")
public class TopicMvcController {
    private final TopicService topicService;
    private final PostMapper postMapper;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public TopicMvcController(TopicService service, PostMapper postMapper, PostService postService, UserService userService) {
        this.topicService = service;
        this.postMapper = postMapper;
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping
    public String showAllTopics(Model model) {
        model.addAttribute("topics", topicService.get(new FilterTopicOptions()));
        return "TopicsView";
    }

    @GetMapping("/{id}")
    public String showSingleTopic(@PathVariable int id, Model model) {
        try {
            String currentUrl = String.format("localhost:8080/topics/%d", id); // Replace this with the actual current URL
            FilterTopicOptions filterTopicOptions = new FilterTopicOptions();
            Topic topic = topicService.get(id);
            model.addAttribute("topic", topic);
            model.addAttribute("posts", topic.getPosts());
            model.addAttribute("tags", topic.getTags());
            model.addAttribute("allTopics", topicService.get(filterTopicOptions));
            model.addAttribute("allUsers", userService.getAllUsers());
            model.addAttribute("currentUrl", currentUrl);
            return "post_details";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    //    @GetMapping("/{title}")
//    public String searchByTopicName (@PathVariable String title, Model model) {
//        FilterTopicOptions filterTopicOptions = new FilterTopicOptions();
//        Optional<String> byTitle = Optional.of(title);
//        filterTopicOptions.setTitle(byTitle);
//        model.addAttribute("topicsByName", service.get(filterTopicOptions));
//
//        return "SearchBarView";
//    }

}