package com.company.web.forum.controllers.MvcController;

import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.helpers.AuthenticationHelper;
import com.company.web.forum.helpers.PostMapper;
import com.company.web.forum.models.*;
import com.company.web.forum.services.PostService;
import com.company.web.forum.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/topics")
public class TopicMvcController {
    private final TopicService service;
    private final PostMapper postMapper;
    private final PostService postService;

    @Autowired
    public TopicMvcController(TopicService service, PostMapper postMapper, PostService postService) {
        this.service = service;
        this.postMapper = postMapper;
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