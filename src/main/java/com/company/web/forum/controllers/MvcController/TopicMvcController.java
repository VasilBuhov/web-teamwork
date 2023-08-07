package com.company.web.forum.controllers.MvcController;

import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.EntityDuplicateException;
import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.helpers.AuthenticationHelper;
import com.company.web.forum.helpers.ParseSmileys;
import com.company.web.forum.helpers.TopicMapper;
import com.company.web.forum.models.*;
import com.company.web.forum.services.TagService;
import com.company.web.forum.services.TopicService;
import com.company.web.forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/topics")
public class TopicMvcController {
    private final TopicService topicService;
    private final TopicMapper topicMapper;
    private final UserService userService;
    private final TagService tagService;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public TopicMvcController(TopicService service, TopicMapper topicMapper, UserService userService, TagService tagService, AuthenticationHelper authenticationHelper) {
        this.topicService = service;
        this.topicMapper = topicMapper;
        this.userService = userService;
        this.tagService = tagService;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public String showAllTopics(Model model) {
        model.addAttribute("topics", topicService.get(new FilterTopicOptions()));
        return "TopicsView";
    }

    @GetMapping("/{id}")
    public String showSingleTopic(@PathVariable int id, Model model, HttpSession session) {
        String username = (String) session.getAttribute("currentUser");
        if (username == null) {
            return "redirect:/auth/login"; // Redirect to the login page
        }
        try {
            User authenticatedUser = userService.getUserByUsername(username);
            authenticationHelper.verifyAuthentication(authenticatedUser.getUsername(), authenticatedUser.getPassword());
            String currentUrl = String.format("localhost:8080/topics/%d", id);
            FilterTopicOptions filterTopicOptions = new FilterTopicOptions();
            Topic topic = topicService.get(id);
            List<Post> postList = new ArrayList<>(topic.getPosts());
            for (Post post : postList) {
                post.setContent(ParseSmileys.replaceSmileys(post.getContent()));
            }
            model.addAttribute("topic", topic);
            model.addAttribute("posts", postList);
            model.addAttribute("tags", topic.getTags());
            model.addAttribute("topТags", tagService.getTopTags());
            model.addAttribute("allTopics", topicService.get(filterTopicOptions));
            model.addAttribute("countUsers", userService.getUsersCount());
            model.addAttribute("currentUrl", currentUrl);
            return "post_details";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @GetMapping("/new")
    public String showNewTopicPage(Model model, HttpSession session) {
        String username = (String) session.getAttribute("currentUser");
        if (username == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("topic", new TopicDto());
        return "ask_question";
    }

    @PostMapping("/new")
    public String createTopic(@Valid @ModelAttribute("topic") TopicDto topicDto, BindingResult errors, Model model, HttpSession session) {
        String username = (String) session.getAttribute("currentUser");
        if (username == null) {
            return "redirect:/auth/login";
        }
        if (errors.hasErrors()) {
            model.addAttribute("errorMessage", "Please fill in all required fields.");
            return "ask_question";
        }
        try {
            Topic newTopic = topicMapper.createTopicDto(topicDto);
            User user = userService.getUserByUsername(username);
            topicService.create(newTopic, user);
            return "redirect:/";
        } catch (EntityDuplicateException e) {
            model.addAttribute("alreadyExists", e.getMessage());
        }

        return "AlreadyExistsView";
    }

    @GetMapping("/edit/{id}")
    public String editTopicPage(@PathVariable int id, Model model, HttpSession session) {
        String username = (String) session.getAttribute("currentUser");
        if (username == null) {
            return "redirect:/auth/login"; // Redirect to the login page
        }
        try {
            Topic topic = topicService.get(id);
            model.addAttribute("topic", topic);
            model.addAttribute("topicDto", topicService.get(id));
            model.addAttribute("tags", tagService.getTopicTags(id));
            return "edit_topic";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @PostMapping("/edit/{id}")
    public String editTopic(@PathVariable int id, @Valid @ModelAttribute("post") TopicDto topicDto, BindingResult errors, Model model, HttpSession session) {
        if (errors.hasErrors()) {
            model.addAttribute("errorMessage", "Please fill in all required fields.");
            return "edit_topic";
        }

        try {
            Topic topic = topicMapper.updateTopicDto(topicDto, id);
            String username = (String) session.getAttribute("currentUser");
            User user = userService.getUserByUsername(username);
            topicService.update(topic, user);
            return "redirect:/topics/" + id;
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";

        } catch (AuthorizationException e) {
            model.addAttribute("error", e.getMessage());
            return "UnauthorizedView";
        }
    }

}