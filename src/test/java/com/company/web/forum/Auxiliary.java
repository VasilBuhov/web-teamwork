package com.company.web.forum;

import com.company.web.forum.models.Tag;
import com.company.web.forum.models.TagDto;
import com.company.web.forum.models.Topic;
import com.company.web.forum.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;

public class Auxiliary {

    public static User createMockAdmin() {
        User mockUser = createMockUser();
        mockUser.setIsAdmin(1);
        return mockUser;
    }

    public static User createMockUser() {
        var mockUser = new User();
        mockUser.setId(69);
        mockUser.setUsername("NoHope");
        mockUser.setPassword("VeryStrongPassword");
        mockUser.setLastName("Nadejda");
        mockUser.setFirstName("Marinova");
        mockUser.setEmail("nadejda.marinova@telerik.com");
        return mockUser;
    }

    public static Tag createMockTag() {
        var mockTag = new Tag();
        mockTag.setId(1);
        mockTag.setName("MockBeer");
        mockTag.setBelongs_to(createMockUser());
        return mockTag;
    }

    public static Topic createMockTopic() {
        var mockTopic = new Topic();
        mockTopic.setId(1);
        mockTopic.setCreator(createMockUser());
        mockTopic.setContent("Mock content");
        mockTopic.setLikes(13);
        mockTopic.setPosts(new HashSet<>());
        mockTopic.setTitle("Put your hands in the air, come on");
        mockTopic.setViews(69);
        return mockTopic;
    }

    public static TagDto createTagDto() {
        TagDto dto = new TagDto();
        dto.setName("Mega Mock TAG");
        dto.setBelongs_to(createMockUser());
        return dto;
    }

    public static String toJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
