package com.company.web.forum.controllers.MvcController;

import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.Tag;
import com.company.web.forum.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tags")
public class TagMvcController {
    private final TagService tagService;

    @Autowired
    public TagMvcController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public String showAllTags(Model model) {
        model.addAttribute("tags", tagService.getAllTags());
        return "TagsView";
    }

    @GetMapping("/{id}")
    public String showSingleTag(@PathVariable int id, Model model) {
        try {
            Tag tag = tagService.getTagById(id);
            model.addAttribute("topic", tag);
            return "TagsView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }
}
