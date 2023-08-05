package com.company.web.forum.controllers.MvcController;

import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.helpers.PostMapper;
import com.company.web.forum.models.Post;
import com.company.web.forum.models.PostDto;
import com.company.web.forum.models.Topic;
import com.company.web.forum.models.User;
import com.company.web.forum.services.PostService;
import com.company.web.forum.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/posts")
public class PostMvcController {
    private final TopicService service;
    private final PostMapper postMapper;
    private final PostService postService;
@Autowired
    public PostMvcController(TopicService service, PostMapper postMapper, PostService postService) {
        this.service = service;
        this.postMapper = postMapper;
        this.postService = postService;
    }
    @PostMapping("/{id}/new")
    public String createPostForTopic(@PathVariable int id, @Valid @ModelAttribute("postDto") PostDto postDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "post_details";
        }
        try {
            Topic topic = service.get(id);
            User user = topic.getCreator();
            Post post = postMapper.CreatePostDto(postDto);
            postService.create(post, user, topic);
            model.addAttribute("successMessage", "Post created successfully!");
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
        return "redirect:/topics/" + id;
    }

}
