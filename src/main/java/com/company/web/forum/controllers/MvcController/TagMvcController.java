package com.company.web.forum.controllers.MvcController;

import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.Tag;
import com.company.web.forum.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/tags")
public class TagMvcController {
    private final TagService tagService;

    @Autowired
    public TagMvcController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public String showAllTags(Model model, Integer page, Integer size) {
        Page<Tag> tagPage = tagService.findPaginated(PageRequest.of(page - 1, size));

        model.addAttribute("tagPage", tagPage);

        int totalPages = tagPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("pageSize", size);
        }

        model.addAttribute("tags", tagService.getAllTags(page, size));
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
