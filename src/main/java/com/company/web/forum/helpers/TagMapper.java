package com.company.web.forum.helpers;

import com.company.web.forum.models.Tag;
import com.company.web.forum.models.TagDto;
import com.company.web.forum.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    private final TagService tagService;

    @Autowired
    public TagMapper(TagService tagService) {
        this.tagService = tagService;
    }


    public Tag fromDto(int id, TagDto dto) {
        Tag tag = fromDto(dto);
        tag.setId(id);
        Tag repositoryTag = tagService.get(id);
        tag.setBelongs_to(repositoryTag.getBelongs_to());
        return tag;
    }

    public Tag fromDto(TagDto dto) {
        Tag tag = new Tag();
        tag.setName(dto.getName());
        tag.setBelongs_to(dto.getBelongs_to());
        return tag;
    }

}