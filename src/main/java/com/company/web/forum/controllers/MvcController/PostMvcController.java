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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/posts")
public class PostMvcController {
    private final TopicService service;
    private final PostMapper postMapper;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostMvcController(TopicService service, PostMapper postMapper, PostService postService, UserService userService) {
        this.service = service;
        this.postMapper = postMapper;
        this.postService = postService;
        this.userService = userService;
    }

    //    @GetMapping("/{id}")
//    public String showSinglePost(@PathVariable int id, Model model) {
//        try {
//            String currentUrl = String.format("localhost:8080/topics/%d", id);
//            FilterTopicOptions filterTopicOptions = new FilterTopicOptions();
//            Post post = postService.get(id);
//            model.addAttribute("topic", topic);
//            model.addAttribute("posts", topic.getPosts());
//            model.addAttribute("tags", topic.getTags());
//            model.addAttribute("toptags", tagService.getTopTags());
//            model.addAttribute("allTopics", topicService.get(filterTopicOptions));
//            model.addAttribute("countUsers", userService.getUsersCount());
//            model.addAttribute("currentUrl", currentUrl);
//            return "post_details";
//        } catch (EntityNotFoundException e) {
//            model.addAttribute("error", e.getMessage());
//            return "NotFoundView";
//        }
//    }
    @GetMapping("/new")
    public String showNewPostPage(Model model, HttpSession session) {
        String username = (String) session.getAttribute("currentUser");
        if (username == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("post", new PostDto());
        return "post_details";
    }
    @PostMapping("/new/{id}")
    public String createPostForTopic(@PathVariable int id, @Valid @ModelAttribute("post") PostDto postDto, BindingResult errors, Model model, HttpSession session) {
        String username = (String) session.getAttribute("currentUser");
        if (username == null) {
            return "redirect:/auth/login";
        }
        if (errors.hasErrors()) {
            model.addAttribute("errorMessage", "Please fill in all required fields.");
        }
        try {
            Topic topic = service.get(id);
            User user = userService.getUserByUsername(username);
            Post post = postMapper.CreatePostDto(postDto);
            postService.create(post, user, topic);
            return "redirect:/topics/" + id;
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @GetMapping("/edit/{id}")
    public String editPostPage(@PathVariable int id, Model model, HttpSession session) {
        String username = (String) session.getAttribute("currentUser");
        if (username == null) {
            return "redirect:/auth/login"; // Redirect to the login page
        }
        try {
            Post post = postService.get(id);
            model.addAttribute("post", post);
            model.addAttribute("postDto", postService.get(id));
            return "edit_post";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable int id, @Valid @ModelAttribute("post") PostDto postDto, BindingResult errors, Model model, HttpSession session) {
        if (errors.hasErrors()) {
            model.addAttribute("errorMessage", "Please fill in all required fields.");
            return "edit_post";
        }

        try {
            Post post = postMapper.updatePostDto(postDto, id);
            String username = (String) session.getAttribute("currentUser");
            User user = userService.getUserByUsername(username);
            postService.update(post, user);
            Topic topic = post.getTopic();
            return "redirect:/topics/" + topic.getId();
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

}
