package com.company.web.forum.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class PostDto {

    @Size(min = 32, max = 8192, message = "Content should be between 32 and 8192 symbols.")
    private String content;

    public PostDto() {
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
