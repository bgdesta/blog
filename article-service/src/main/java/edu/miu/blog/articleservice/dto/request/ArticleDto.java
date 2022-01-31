package edu.miu.blog.articleservice.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ArticleDto {

    private String title;
    private String summary;
    private String content;
}
