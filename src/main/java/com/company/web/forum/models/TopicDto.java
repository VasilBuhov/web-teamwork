package com.company.web.forum.models;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TopicDto {
    @NotNull(message = "Title can't be empty.")
    @Size(min = 16, max = 64, message = "Name should be between 16 and 64 symbols.")
    private String title;
    @Size(min = 32, max = 8192, message = "Content should be between 32 and 8192 symbols.")
    private String content;
//    @NotNull(message = "Tag can't be empty.")
//    private Tag tag;

    public TopicDto() {}


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    public Tag getTag() {
//        return tag;
//    }
//
//    public void setTag(Tag tag) {
//        this.tag = tag;
//    }
}
