package edu.miu.blog.articleservice.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostArticleDto extends ArticleDto{
    @NotEmpty(message = "* Title can't be empty")
    private String title;

    @NotEmpty(message = "* Summary can't be empty")
    private String summary;

    @NotEmpty(message = "* Content can't be empty")
    private String content;
}
