package com.company.web.forum.controllers.MvcController;

import com.company.web.forum.exceptions.EntityDuplicateException;
import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.helpers.AuthenticationHelper;
import com.company.web.forum.helpers.TopicMapper;
import com.company.web.forum.models.*;
import com.company.web.forum.services.TopicService;
import com.company.web.forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/topics")
public class TopicMvcController {
    private final TopicService topicService;
    private final TopicMapper topicMapper;
    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public TopicMvcController(TopicService service, TopicMapper topicMapper, UserService userService, AuthenticationHelper authenticationHelper) {
        this.topicService = service;
        this.topicMapper = topicMapper;
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
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

    @GetMapping("/new")
    public String showNewTopicPage(Model model){
        model.addAttribute("topic", new TopicDto());
        return "ask_question";
    }

    @PostMapping("/new")
    public String createTopic(@Valid @RequestHeader HttpHeaders httpHeaders, @ModelAttribute("topic") TopicDto topicDto, BindingResult errors, Model model) {
        if(errors.hasErrors()){
            model.addAttribute("errorMessage", "Please fill in all required fields.");
            return "ask_question";
        }
        try {
            User authenticatedUser = authenticationHelper.tryGetUser(httpHeaders);
            Topic newTopic = topicMapper.createTopicDto(topicDto);
            topicService.create(newTopic, authenticatedUser);
            return "redirect:/" ;
        } catch (EntityDuplicateException e) {
            model.addAttribute("alreadyExists", e.getMessage());
        }

        return "AlreadyExistsView";
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