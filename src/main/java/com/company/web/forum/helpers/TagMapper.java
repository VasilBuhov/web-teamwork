package com.company.web.forum.helpers;

import com.company.web.forum.models.Tag;
import com.company.web.forum.models.TagDto;
import com.company.web.forum.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagMapper {

    private final TagService tagService;

    @Autowired
    public TagMapper(TagService tagService) {
        this.tagService = tagService;
    }

    public TagDto toDto(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setName(tag.getName());
        tagDto.setBelongs_to(tag.getBelongs_to());
        tagDto.setOccurrenceIn(tag.getOccurrenceIn());
        return tagDto;
    }


    public Tag fromDto(int id, TagDto dto) {
        Tag tag = fromDto(dto);
        tag.setId(id);
        Tag repositoryTag = tagService.getTagById(id);
        tag.setBelongs_to(repositoryTag.getBelongs_to());
        return tag;
    }

    public Tag fromDto(TagDto dto) {
        Tag tag = new Tag();
        tag.setName(dto.getName());
        tag.setBelongs_to(dto.getBelongs_to());
        tag.setOccurrenceIn(dto.getOccurrenceIn());
        tag.setBelongs_to(dto.getBelongs_to());
        return tag;
    }

    public List<TagDto> toDtoList(List<Tag> tags) {
        return tags.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

//    public List<Tag> toDtoTagList(List<Tag> tags) {
//        return tags.stream()
//                .map(this::toDto)
//                .collect(Collectors.toList());
//    }

}