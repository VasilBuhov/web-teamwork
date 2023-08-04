package com.company.web.forum.controllers.MvcController;

import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.FilterTopicOptions;
import com.company.web.forum.models.Topic;
import com.company.web.forum.services.PostService;
import com.company.web.forum.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/topics")
public class TopicMvcController {
    private final TopicService service;

    private final PostService postService;

    @Autowired
    public TopicMvcController(TopicService service, PostService postService) {
        this.service = service;
        this.postService = postService;
    }

    @GetMapping
    public String showAllTopics(Model model) {
        model.addAttribute("topics", service.get(new FilterTopicOptions()));
        return "TopicsView";
    }

    @GetMapping("/{id}")
    public String showSingleTopic(@PathVariable int id, Model model) {
        try {
            Topic topic = service.get(id);
            model.addAttribute("topic", topic);
            model.addAttribute("posts", topic.getPosts());
            model.addAttribute("tags", topic.getTags());
            return "post_details";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }
}
