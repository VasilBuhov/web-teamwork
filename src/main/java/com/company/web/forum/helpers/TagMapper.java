package com.company.web.forum.helpers;

import com.company.web.forum.models.Tag;
import com.company.web.forum.models.TagDto;
import com.company.web.forum.services.StyleService;
import com.company.web.forum.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    private final TagService tagService;

    private final StyleService styleService;

    @Autowired
    public TagMapper(TagService tagService, StyleService styleService) {
        this.tagService = tagService;
        this.styleService = styleService;
    }


    public Tag fromDto(int id, TagDto dto) {
        Tag tag = fromDto(dto);
        tag.setId(id);
        Tag repositoryTag = tagService.get(id);
        tag.setCreatedBy(repositoryTag.getCreatedBy());
        return tag;
    }

    public Tag fromDto(TagDto dto) {
        Tag tag = new Tag();
        tag.setName(dto.getName());
        tag.setContent(dto.getContent());
        tag.setStyle(styleService.get(dto.getStyleId()));
        return tag;
    }

}