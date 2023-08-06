package com.company.web.forum.controllers.MvcController;

import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.FilterTopicOptions;
import com.company.web.forum.models.Tag;
import com.company.web.forum.services.TagService;
import com.company.web.forum.services.TopicService;
import com.company.web.forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/tags")
public class TagMvcController {
    private final TagService tagService;
    private final TopicService topicService;
    private final UserService userService;

    @Autowired
    public TagMvcController(TagService tagService, TopicService topicService, UserService userService) {
        this.tagService = tagService;
        this.topicService = topicService;
        this.userService = userService;
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
            FilterTopicOptions filterTopicOptions = new FilterTopicOptions();
            Optional<String> byTag = Optional.of(tagService.getTagById(id).getName());
            filterTopicOptions.setTagTitle(byTag);
            FilterTopicOptions noFilterTopicOptions = new FilterTopicOptions();


            Tag tag = tagService.getTagById(id);
            model.addAttribute("tag", tag);
            model.addAttribute("topicList", topicService.get(filterTopicOptions));
            model.addAttribute("allTopics", topicService.get(noFilterTopicOptions));
            model.addAttribute("countUsers", userService.getUsersCount());
            model.addAttribute("topTags", tagService.getTopTags());
            return "tags_view";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }
}
